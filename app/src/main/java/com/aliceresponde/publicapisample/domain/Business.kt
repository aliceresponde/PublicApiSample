package com.aliceresponde.publicapisample.domain

import android.os.Parcelable
import com.aliceresponde.publicapisample.data.local.BusinessEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Business(
    val id: String,
    val name: String,
    val phone: String,
    val address: String,
    val imageUrl: String,
    val rating: Float = 0.0f,
    val isClosed: Boolean
): Parcelable

fun BusinessEntity.toBusiness() =
    Business(
        id = id,
        name = name,
        phone = phone,
        address = address,
        imageUrl = imageUrl,
        rating = rating,
        isClosed = isClosed
    )

fun List<BusinessEntity>.toBusiness(): List<Business> = this.map { it.toBusiness() }

