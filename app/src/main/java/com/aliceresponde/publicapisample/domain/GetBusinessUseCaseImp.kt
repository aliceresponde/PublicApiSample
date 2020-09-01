package com.aliceresponde.publicapisample.domain

import com.aliceresponde.publicapisample.data.repository.BusinessRepository
import com.aliceresponde.publicapisample.data.repository.ErrorState
import com.aliceresponde.publicapisample.data.repository.SuccessState

class GetBusinessUseCaseImp(private val repository: BusinessRepository) : GetBusinessUseCase {
    override suspend fun invoke(
        locale: String,
        hasInternetAccess: Boolean
    ): UiState<List<Business>> {
        return when (val result = repository.getBusinessByLocation(locale, hasInternetAccess)) {
            is SuccessState -> SuccessViewState(
                data = result.data?.let { entity -> entity.toBusiness() } ?: listOf()
            )
            is ErrorState -> ErrorViewState(
                message = result.message ?: "",
                data = result.data?.let { entity -> entity.toBusiness() } ?: listOf())
        }
    }
}