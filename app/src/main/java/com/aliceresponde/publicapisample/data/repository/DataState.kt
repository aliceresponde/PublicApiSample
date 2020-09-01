package com.aliceresponde.publicapisample.data.repository


sealed class DataState<T>(
    val data: T? = null,
    val message: String? = null
) {
    companion object{
        const val EMPTY_DATA_MESSAGE = "No Data Available to show"
        const val INTERNET_FAILURE_DATA_MESSAGE = "Internet Error"
    }
}
class SuccessState<T>(data: T) : DataState<T>(data)
class ErrorState<T>(message: String, data: T? = null) : DataState<T>(data, message)