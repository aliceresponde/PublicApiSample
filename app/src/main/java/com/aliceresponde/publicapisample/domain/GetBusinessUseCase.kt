package com.aliceresponde.publicapisample.domain

interface GetBusinessUseCase {
    suspend operator fun invoke(locale: String, hasInternetAccess: Boolean): UiState<List<Business>>
}
