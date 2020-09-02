package com.aliceresponde.publicapisample.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "business_table")
data class BusinessEntity(
    @PrimaryKey val id: String,
    val name: String,
    val imageUrl: String = "",
    val phone: String = "",
    val address: String = "",
    val rating: Float = 0.0f,
    val isClosed: Boolean,
    val location: String
)