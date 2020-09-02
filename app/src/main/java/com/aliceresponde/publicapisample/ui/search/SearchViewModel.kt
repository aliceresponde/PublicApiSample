package com.aliceresponde.publicapisample.ui.search

import android.view.View.GONE
import android.view.View.VISIBLE
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
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class SearchViewModel @ViewModelInject constructor(private val getBusiness: GetBusinessUseCase) :
    ViewModel() {
    private val _businessData = MutableLiveData<Event<List<Business>>>()
    val businessData: LiveData<Event<List<Business>>> get() = _businessData

    private val _selectedLocation = MutableLiveData<Event<String>>()
    val selectedLocation: LiveData<Event<String>> get() = _selectedLocation

    private val _viewState = MutableLiveData<SearchViewState>()
    val viewState: LiveData<SearchViewState> get() = _viewState

    private val _noDataVisibility = MutableLiveData(GONE)
    val noDataVisibility: LiveData<Int> get() = _noDataVisibility

    private val _recyclerVisibility = MutableLiveData(GONE)
    val recyclerVisibility: LiveData<Int> get() = _recyclerVisibility

    private val _loadingVisibility = MutableLiveData(GONE)
    val loadingVisibility: LiveData<Int> get() = _loadingVisibility

    fun getBusiness(location: String, isInternetConnected: Boolean) {
        viewModelScope.launch(Main) {
            _viewState.value = Loading
            when (val response = getBusiness.invoke(location, isInternetConnected)) {
                is SuccessViewState -> {
                    _viewState.value =
                        if (response.data.isEmpty()) NoData
                        else ShowData(response.data)
                }
                is ErrorViewState -> response.data?.let {
                    _viewState.value = if (it.isEmpty()) NoData else ShowData(it)
                }
            }
        }
    }

    fun updateSelectedLocation(location: String) {
        _selectedLocation.value = Event(location)
    }

    fun showLoading() {
        _loadingVisibility.value = VISIBLE
        _recyclerVisibility.value = GONE
        _noDataVisibility.value = GONE
    }

    fun showData() {
        _loadingVisibility.value = GONE
        _recyclerVisibility.value = VISIBLE
        _noDataVisibility.value = GONE
    }

    fun showNoData() {
        _loadingVisibility.value = GONE
        _recyclerVisibility.value = GONE
        _noDataVisibility.value = VISIBLE
    }
}

sealed class SearchViewState {
    object Loading : SearchViewState()
    object NoData : SearchViewState()
    class ShowData(val data: List<Business>) : SearchViewState()
}