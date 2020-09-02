package com.aliceresponde.publicapisample.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.aliceresponde.countingapp.presentation.common.EventObserver
import com.aliceresponde.publicapisample.R
import com.aliceresponde.publicapisample.data.remote.NetworkConnection
import com.aliceresponde.publicapisample.databinding.FragmentSearchBinding
import com.aliceresponde.publicapisample.domain.Business
import com.aliceresponde.publicapisample.ui.search.SearchViewState.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private val adapter: BusinessAdapter by lazy { BusinessAdapter(callback = ::navigateToDetail) }

    @Inject
    lateinit var networkConnection: NetworkConnection

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false).apply {
            viewModel = SearchFragment@ viewModel
            lifecycleOwner = this@SearchFragment

            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.locals_array,
                android.R.layout.simple_dropdown_item_1line
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    parent?.let {
                        val location = it.getItemAtPosition(position).toString()
                        this@SearchFragment.viewModel.updateSelectedLocation(location)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            }
        }

        setupObservers()
        return binding.root
    }

    private fun setupObservers() {
        viewModel.selectedLocation.observe(viewLifecycleOwner, EventObserver {
            viewModel.getBusiness(it, this@SearchFragment.isInternetConnected())
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Loading -> viewModel.showLoading()
                is ShowData -> viewModel.showData()
                is NoData -> viewModel.showNoData()
            }
        })

        viewModel.businessData.observe(viewLifecycleOwner, EventObserver { adapter.update(it) })
    }

    private fun isInternetConnected(): Boolean {
        var connection = true
        lifecycleScope.launch {
            connection = withContext(IO) { networkConnection.isConnected() }
        }
        return connection
    }

    private fun navigateToDetail(business: Business) {
        val action = SearchFragmentDirections.actionSearchFragmentToBusinessDetailFragment(business)
        findNavController().navigate(action)
    }
}
