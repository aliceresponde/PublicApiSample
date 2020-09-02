package com.aliceresponde.publicapisample.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliceresponde.countingapp.presentation.common.Event
import com.aliceresponde.publicapisample.domain.Business
import com.aliceresponde.publicapisample.domain.GetBusinessUseCase
import com.aliceresponde.publicapisample.domain.UiState.ErrorViewState
import com.aliceresponde.publicapisample.domain.UiState.SuccessViewState
import com.aliceresponde.publicapisample.ui.search.SearchViewState.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel @ViewModelInject constructor(private val getBusiness: GetBusinessUseCase) :
    ViewModel() {
    private val _businessData = MutableLiveData<Event<List<Business>>>()
    val businessData: LiveData<Event<List<Business>>> get() = _businessData

    private val _selectedLocation = MutableLiveData<Event<String>>()
    val selectedLocation: LiveData<Event<String>> get() = _selectedLocation

    private val _viewState = MutableLiveData<Event<SearchViewState>>()
    val viewState: LiveData<Event<SearchViewState>> get() = _viewState

    fun getBusiness(location: String, isInternetConnected: Boolean) {
        viewModelScope.launch(Main) {
            withContext(IO) {
                _viewState.postValue(Event(Loading))
                when (val response = getBusiness.invoke(location, isInternetConnected)) {
                    is SuccessViewState -> {
                        _businessData.postValue(Event(response.data))
                        _viewState.postValue(
                            if (response.data.isEmpty()) Event(NoData)
                            else Event(ShowData(response.data))
                        )
                    }
                    is ErrorViewState -> response.data?.let {
                        _viewState.postValue(if (response.data.isEmpty()) Event(NoData)
                        else Event(ShowData(response.data)))
                    }
                }
            }
        }
    }

    fun updateSelectedLocation(location: String) {
        _selectedLocation.value = Event(location)
    }
}

sealed class SearchViewState {
    object Loading : SearchViewState()
    object NoData : SearchViewState()
    class ShowData(val data: List<Business>) : SearchViewState()
}