package com.aliceresponde.publicapisample.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface BusinessDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<BusinessEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBusiness(business: BusinessEntity)

    @Query("SELECT * from BUSINESS_TABLE WHERE id = :businessId")
    suspend fun getBusinessById(businessId: String): BusinessEntity

    @Query("SELECT * FROM BUSINESS_TABLE WHERE location = :location")
    suspend fun getBusinessByLocation(location: String): List<BusinessEntity>

    @Query("DELETE FROM BUSINESS_TABLE")
    suspend fun deleteAllBusiness()

    @Query("SELECT COUNT(*) FROM BUSINESS_TABLE WHERE location = :location")
    fun getCount(location: String): Int
}