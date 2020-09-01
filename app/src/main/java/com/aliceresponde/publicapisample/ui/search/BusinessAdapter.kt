package com.aliceresponde.publicapisample.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aliceresponde.countingapp.presentation.common.loadFromUrl
import com.aliceresponde.publicapisample.R
import com.aliceresponde.publicapisample.databinding.BusinessItemBinding
import com.aliceresponde.publicapisample.domain.Business

class BusinessAdapter(private val data: MutableList<Business> = mutableListOf(), val callback: (Business) -> Unit) :
    RecyclerView.Adapter<BusinessAdapter.BusinessHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.business_item, parent, false)
        return BusinessHolder(view)
    }

    override fun onBindViewHolder(holder: BusinessHolder, position: Int) =
        holder.onBind(data[position])

    override fun getItemCount(): Int = data.size

    inner class BusinessHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = BusinessItemBinding.bind(itemView)

        fun onBind(business: Business) {
            binding.apply {
                with(business) {
                    businessName.text = name
                    businessPhone.text = phone
                    businessImageUrl.loadFromUrl(imageUrl)
                    root.setOnClickListener {
                        callback.invoke(business)
                    }
                }
            }
        }
    }

}
