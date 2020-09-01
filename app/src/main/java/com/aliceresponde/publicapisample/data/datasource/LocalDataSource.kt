package com.aliceresponde.publicapisample.data.datasource

import com.aliceresponde.publicapisample.data.local.BusinessEntity

interface LocalDataSource {
    suspend fun insertAll(data: List<BusinessEntity>)
    suspend fun insertBusiness(item: BusinessEntity)
    suspend fun getBusinessDetail(id: String) : BusinessEntity
    suspend fun getBusinessByLocation(locale: String): List<BusinessEntity>
    suspend fun deleteAll()
}