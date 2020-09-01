package com.aliceresponde.publicapisample.data.local

import com.aliceresponde.publicapisample.data.datasource.LocalDataSource
import javax.inject.Inject

class RoomDataSource @Inject constructor(private val db: AppDatabase): LocalDataSource {

    private val dao = db.getBusinessDao()

    override suspend fun insertAll(data: List<BusinessEntity>) {
        dao.insertAll(data)
    }

    override suspend fun insertBusiness(item: BusinessEntity) {
        dao.insertBusiness(item)
    }

    override suspend fun getBusinessDetail(id: String): BusinessEntity {
        return dao.getBusinessById(id)
    }

    override suspend fun getBusinessByLocation(locale: String): List<BusinessEntity> {
        return dao.getBusinessByLocale(locale)
    }

    override suspend fun deleteAll() {
        dao.deleteAllBusiness()
    }
}