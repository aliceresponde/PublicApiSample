package com.aliceresponde.publicapisample.data.datasource

import com.aliceresponde.publicapisample.data.remote.BusinessDetailResponse
import com.aliceresponde.publicapisample.data.remote.BusinessResponse
import retrofit2.Response

interface RemoteDataSource {
    suspend  fun getBusinessOn(location: String) : Response<BusinessResponse>
    suspend fun getBusinessDetail(id: String): Response<BusinessDetailResponse>
}