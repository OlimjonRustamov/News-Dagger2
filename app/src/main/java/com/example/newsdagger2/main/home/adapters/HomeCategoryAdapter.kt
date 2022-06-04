package com.example.newsdagger2.main.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.newsdagger2.R
import com.example.newsdagger2.databinding.ItemHomeCategoryBinding

class HomeCategoryAdapter(val context: Context, val categories: ArrayList<String>) :
    RecyclerView.Adapter<HomeCategoryAdapter.Vh>() {

    var homeCategoryClickListener: HomeCategoryClickListener? = null

    inner class Vh(var binding: ItemHomeCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(category: String, position: Int) {
            binding.homeCategoryName.text = category
            binding.homeCategoryName.setOnClickListener {
                if (homeCategoryClickListener != null) {
                    homeCategoryClickListener!!.onClick(category, position)
                    it.isSelected = true
                }
                binding.homeCategoryName.setTextColor(ContextCompat.getColor(context, R.color.white))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemHomeCategoryBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(categories[position], position)
    }

    override fun getItemCount(): Int =categories.size

    interface HomeCategoryClickListener{
        fun onClick(category: String, position: Int)
    }
}