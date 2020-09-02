package com.aliceresponde.publicapisample.data.repository

import android.util.Log
import com.aliceresponde.publicapisample.data.datasource.LocalDataSource
import com.aliceresponde.publicapisample.data.datasource.RemoteDataSource
import com.aliceresponde.publicapisample.data.local.BusinessEntity
import com.aliceresponde.publicapisample.data.remote.BusinessDTO
import com.aliceresponde.publicapisample.data.remote.BusinessDetailResponse
import com.aliceresponde.publicapisample.data.repository.DataState.ErrorState
import com.aliceresponde.publicapisample.data.repository.DataState.SuccessState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class BusinessRepositoryImp(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) : BusinessRepository {
    override suspend fun getBusinessByLocation(
        location: String,
        hasInternetAccess: Boolean
    ): DataState<List<BusinessEntity>> {
        try {
            if (!hasInternetAccess) {
                return SuccessState(getBusinessListFromCache(location))
            } else {
                val response = withContext(IO) { remote.getBusinessOn(location) }
                return if (response.isSuccessful) {
                    withContext(IO) {
                        val result = response.body()?.businesses ?: listOf()
                        val entities = result.map { dto -> dto.toEntity() }
                        local.insertAll(entities)
                        val localData = getBusinessListFromCache(location)
                        val i = 3
                        SuccessState(localData)
                    }
                } else {
                    return ErrorState(
                        data = local.getBusinessByLocation(location),
                        message = DataState.INTERNET_FAILURE_DATA_MESSAGE
                    )
                }
            }
        } catch (e: Throwable) {
            return ErrorState(
                data = local.getBusinessByLocation(location),
                message = DataState.INTERNET_FAILURE_DATA_MESSAGE
            )
        }
    }


    override suspend fun getBusinessDetailById(
        id: String,
        hasInternetAccess: Boolean
    ): DataState<BusinessEntity> {
        try {
            if (!hasInternetAccess) {
                return SuccessState(getBusinessItemFromCache(id))
            } else {
                val response = withContext(IO) { remote.getBusinessDetail(id) }
                if (response.isSuccessful) {
                    response.body()?.let {
                        val data = withContext(IO) {
                            local.insertBusiness(it.toEntity())
                            getBusinessItemFromCache(id)
                        }
                        return SuccessState(data)
                    } ?: return ErrorState(message = DataState.INTERNET_FAILURE_DATA_MESSAGE)
                } else {
                    return ErrorState(
                        data = local.getBusinessDetail(id),
                        message = DataState.INTERNET_FAILURE_DATA_MESSAGE
                    )
                }
            }
        } catch (e: Throwable) {
            return ErrorState(
                data = local.getBusinessDetail(id),
                message = e.message ?: DataState.INTERNET_FAILURE_DATA_MESSAGE
            )
        }
    }

    private suspend fun getBusinessListFromCache(locale: String): List<BusinessEntity> {
        return local.getBusinessByLocation(locale)
    }

    private suspend fun getBusinessItemFromCache(id: String): BusinessEntity {
        return withContext(IO) { local.getBusinessDetail(id) }
    }

    private fun BusinessDTO.toEntity(): BusinessEntity = BusinessEntity(
        id = id,
        name = name,
        imageUrl = imageUrl,
        phone = phone,
        address = location.toString(),
        rating = rating,
        isClosed = isClosed,
        location = location.displayAddress.last()
    )

    private fun BusinessDetailResponse.toEntity(): BusinessEntity = BusinessEntity(
        id = id,
        name = name,
        imageUrl = imageUrl,
        phone = phone,
        rating = rating,
        isClosed = isClosed,
        location = location.displayAddress.last()
    )
}