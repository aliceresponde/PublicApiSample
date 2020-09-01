package com.aliceresponde.publicapisample.data.repository

import com.aliceresponde.publicapisample.data.datasource.LocalDataSource
import com.aliceresponde.publicapisample.data.datasource.RemoteDataSource
import com.aliceresponde.publicapisample.data.local.BusinessEntity
import com.aliceresponde.publicapisample.data.remote.BusinessDTO
import com.aliceresponde.publicapisample.data.remote.BusinessDetailResponse
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
                    val result = response.body()?.business
                    val entities = result?.map { it.toEntity() }
                    entities?.let { local.insertAll(it) }
                    SuccessState(getBusinessListFromCache(location))
                } else {
                    ErrorState(
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
        return withContext(IO) { local.getBusinessByLocation(locale) }
    }

    private suspend fun getBusinessItemFromCache(id: String): BusinessEntity {
        return withContext(IO) { local.getBusinessDetail(id) }
    }


    fun BusinessDTO.toEntity(): BusinessEntity = BusinessEntity(
        id = id,
        name = name,
        imageUrl = imageUrl,
        phone = phone,
        address = location.toString(),
        rating = rating,
        isClosed = isClosed,
        locale = location.country
    )

    private fun BusinessDetailResponse.toEntity(): BusinessEntity = BusinessEntity(
        id = id,
        name = name,
        imageUrl = imageUrl,
        phone = phone,
        rating = rating,
        isClosed = isClosed,
        locale = location.country
    )
}