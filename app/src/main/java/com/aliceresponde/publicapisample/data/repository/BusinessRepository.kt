package com.aliceresponde.publicapisample.data.repository

import com.aliceresponde.publicapisample.data.local.BusinessEntity

interface BusinessRepository {
    suspend fun getBusinessByLocation(locale: String, hasInternetAccess: Boolean) : DataState<List<BusinessEntity>>
    suspend fun getBusinessDetailById(id:String, hasInternetAccess: Boolean): DataState<BusinessEntity>
}
