package com.aliceresponde.publicapisample.domain

import com.aliceresponde.publicapisample.data.repository.BusinessRepository
import com.aliceresponde.publicapisample.data.repository.ErrorState
import com.aliceresponde.publicapisample.data.repository.SuccessState

class GetBusinessDetailUseCaseImp(private val repository: BusinessRepository) :
    GetBusinessDetailUseCase {
    override suspend fun getBusinessDetail(
        id: String,
        hasInternetConnection: Boolean
    ): UiState<Business> {
        return when (val result = repository.getBusinessDetailById(id, hasInternetConnection)) {
            is SuccessState -> SuccessViewState(data = result.data!!.toBusiness())
            is ErrorState -> ErrorViewState(message = result?.message ?: "")
        }
    }
}