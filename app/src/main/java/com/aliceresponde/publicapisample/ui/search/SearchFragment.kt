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
import com.aliceresponde.publicapisample.R
import com.aliceresponde.publicapisample.databinding.FragmentSearchBinding
import com.aliceresponde.publicapisample.domain.Business

class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private val adapter: BusinessAdapter by lazy { BusinessAdapter(callback = ::navigateToDetail) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false).apply {
            viewModel = SearchFragment@ viewModel
            lifecycleOwner = this@SearchFragment
            spinner.adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.locals_array,
                android.R.layout.simple_dropdown_item_1line
            )

            businessRecycler.adapter = adapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val location = parent?.getItemAtPosition(position).toString()
                    viewModel.getBusiness(location)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            }

        }

        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            when (it) {
                SearchViewState.Loading -> showLoading()
                SearchViewState.InternetError -> TODO()
                is SearchViewState.ShowData -> TODO()
            }
        })

        return binding.root
    }

    private fun showLoading() {
        TODO("Not yet implemented")
    }

    private fun navigateToDetail(business: Business) {

    }
}