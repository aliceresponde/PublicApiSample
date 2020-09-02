package com.aliceresponde.publicapisample.data.local

import com.aliceresponde.publicapisample.data.datasource.LocalDataSource
import javax.inject.Inject

class RoomDataSource @Inject constructor(private val db: AppDatabase): LocalDataSource {

    private val dao = db.businessDao()


    override suspend fun insertAll(data: List<BusinessEntity>) {
        dao.insertAll(data)
    }

    override suspend fun insertBusiness(item: BusinessEntity) {
        dao.insertBusiness(item)
    }

    override suspend fun getBusinessDetail(id: String): BusinessEntity {
        return dao.getBusinessById(id)
    }

    override suspend fun getBusinessByLocation(location: String): List<BusinessEntity> {
        return dao.getBusinessByLocation(location)
    }

    override suspend fun deleteAll() {
        dao.deleteAllBusiness()
    }

    override suspend fun countItems(location: String): Int {
        return dao.getCount(location)
    }
}