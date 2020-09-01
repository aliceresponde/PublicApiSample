package com.aliceresponde.publicapisample.domain

interface GetBusinessDetailUseCase {
    suspend fun getBusinessDetail(id: String, hasInternetConnection: Boolean) : UiState<Business>
}