package com.aliceresponde.publicapisample.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aliceresponde.countingapp.presentation.common.loadFromUrl
import com.aliceresponde.publicapisample.R
import com.aliceresponde.publicapisample.databinding.BusinessItemBinding
import com.aliceresponde.publicapisample.domain.Business

class BusinessAdapter(
    private var data: MutableList<Business> = mutableListOf(),
    val callback: (Business) -> Unit
) :
    RecyclerView.Adapter<BusinessAdapter.BusinessHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Business>() {
        override fun areItemsTheSame(oldItem: Business, newItem: Business): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Business, newItem: Business): Boolean =
            oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, differCallback)

    fun update(newBusinessList: List<Business>) {
        differ.submitList(newBusinessList)
        data = ArrayList(newBusinessList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.business_item, parent, false)
        return BusinessHolder(view)
    }

    override fun onBindViewHolder(holder: BusinessHolder, position: Int) =
        holder.onBind(data[position])

    override fun getItemCount(): Int = differ.currentList.size

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
