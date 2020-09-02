package com.aliceresponde.publicapisample.data.remote

import com.google.gson.annotations.SerializedName

data class BusinessDetailResponse(
    val id: String,
    val name: String,
    @SerializedName("image_url")
    val imageUrl: String,
    val phone: String,
    val rating: Float,
    @SerializedName("is_closed")
    val isClosed: Boolean,
    val location: LocationDTO
){
    data class LocationDTO(
        val address1: String,
        val city: String,
        val state: String,
        val country: String,
        @SerializedName("display_address")
        val displayAddress: List<String>
    ) {
        override fun toString(): String = "$address1 \n $city, $state in ($country)"
    }

}


