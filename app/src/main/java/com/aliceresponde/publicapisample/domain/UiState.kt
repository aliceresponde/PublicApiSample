package com.aliceresponde.publicapisample.domain


sealed class UiState<out T> {
    companion object {
        const val EMPTY_DATA_MESSAGE = "No Data Available to show"
    }

    class SuccessViewState<out T>(val data: T) : UiState<T>()
    class ErrorViewState<out T>(val message: String ="", val data: T? = null) : UiState<T>()
}
