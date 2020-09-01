package com.aliceresponde.publicapisample.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliceresponde.countingapp.presentation.common.Event
import com.aliceresponde.publicapisample.data.remote.NetworkConnection
import com.aliceresponde.publicapisample.databinding.BusinessItemBinding
import com.aliceresponde.publicapisample.domain.Business
import com.aliceresponde.publicapisample.domain.ErrorViewState
import com.aliceresponde.publicapisample.domain.GetBusinessUseCase
import com.aliceresponde.publicapisample.domain.SuccessViewState
import com.aliceresponde.publicapisample.ui.search.SearchViewState.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel @ViewModelInject constructor(
    private val getBusiness: GetBusinessUseCase,
    private val networkConnection: NetworkConnection
) :
    ViewModel() {
    private val _businessData = MutableLiveData<Event<List<BusinessItemBinding>>>()
    val businessData: LiveData<Event<List<BusinessItemBinding>>> get() = _businessData

    private val _noDataVisibility = MutableLiveData<Int>()
    val noDataVisibility: LiveData<Int> get() = _noDataVisibility

    private val _connectionErrorVisibility = MutableLiveData<Int>()
    val connectionErrorVisibility: LiveData<Int> get() = _connectionErrorVisibility

    private val _viewState = MutableLiveData<SearchViewState>()
    val viewState: LiveData<SearchViewState> get() = _viewState



    fun getBusiness(location: String) {
        viewModelScope.launch(Main) {
            _viewState.value = Loading
            val response =
                withContext(IO) { getBusiness.invoke(location, networkConnection.isConnected()) }
            when (response) {
                is SuccessViewState -> response.data?.let {
                    _viewState.value = if (it.isEmpty()) NoData else ShowData(it)
                }
                is ErrorViewState -> _viewState.value = InternetError
            }
        }
    }
}

sealed class SearchViewState {
    object Loading : SearchViewState()
    object InternetError : SearchViewState()
    object NoData : SearchViewState()
    class ShowData(data: List<Business>) : SearchViewState()
}