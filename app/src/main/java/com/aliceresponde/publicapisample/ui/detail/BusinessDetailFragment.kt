package com.aliceresponde.publicapisample.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.aliceresponde.countingapp.presentation.common.loadFromDrawable
import com.aliceresponde.countingapp.presentation.common.loadFromUrl
import com.aliceresponde.publicapisample.R
import com.aliceresponde.publicapisample.databinding.FragmentDetailBinding
import com.aliceresponde.publicapisample.domain.Business
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BusinessDetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val args: BusinessDetailFragmentArgs by navArgs()
    private val item: Business by lazy { args.item }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false).apply {
            name.text = item.name
            image.loadFromUrl(item.imageUrl)
            phone.text = item.phone
            rating.text = item.rating.toString()
            address.text = item.address
            if (item.isClosed)
                isClosed.loadFromDrawable(R.drawable.ic_close)
            else isClosed.loadFromDrawable(R.drawable.ic_open)

        }
        return binding.root
    }
}