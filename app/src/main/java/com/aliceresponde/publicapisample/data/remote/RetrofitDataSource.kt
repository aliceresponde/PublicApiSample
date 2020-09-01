package com.aliceresponde.publicapisample.data.remote

import com.aliceresponde.publicapisample.data.datasource.RemoteDataSource
import retrofit2.Response

class RetrofitDataSource(private val service: YelpApiService) : RemoteDataSource {
    override suspend fun getBusinessOn(location: String): Response<BusinessResponse> {
        return service.getBusinessByLocation(location)
    }

    override suspend fun getBusinessDetail(id: String): Response<BusinessDetailResponse> {
        return service.getBusinessDetailsById(id)
    }
}