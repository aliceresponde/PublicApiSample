package com.aliceresponde.publicapisample.domain

import com.aliceresponde.publicapisample.data.repository.BusinessRepository
import com.aliceresponde.publicapisample.data.repository.DataState
import com.aliceresponde.publicapisample.domain.UiState.*


class GetBusinessDetailUseCaseImp(private val repository: BusinessRepository) :
    GetBusinessDetailUseCase {
    override suspend fun getBusinessDetail(
        id: String,
        hasInternetConnection: Boolean
    ): UiState<Business> {
        return when (val result = repository.getBusinessDetailById(id, hasInternetConnection)) {
            is DataState.SuccessState -> SuccessViewState(data = result.data.toBusiness())
            is DataState.ErrorState -> ErrorViewState(message = result.message )
        }
    }
}