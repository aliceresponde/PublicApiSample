package com.aliceresponde.publicapisample.data.repository


sealed class DataState<out T>{
    companion object{
        const val EMPTY_DATA_MESSAGE = "No Data Available to show"
        const val INTERNET_FAILURE_DATA_MESSAGE = "Internet Error"
    }
    class SuccessState<out T>(val data: T) : DataState<T>()
    class ErrorState<out T>(val message: String, val data: T? = null) : DataState<T>()
}
