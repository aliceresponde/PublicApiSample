package com.aliceresponde.publicapisample.domain

import com.aliceresponde.publicapisample.data.local.BusinessEntity

data class Business(val id: String, val name: String, val phone: String, val imageUrl: String)

fun BusinessEntity.toBusiness() =
    Business(
        id = id,
        phone = phone,
        name = name,
        imageUrl = imageUrl
    )

fun List<BusinessEntity>.toBusiness(): List<Business> = this.map { it.toBusiness() }

