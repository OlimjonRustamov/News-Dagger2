package com.example.newsdagger2.main.categories

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsdagger2.databinding.ItemCategoryBinding

class CategoryAdapter(val context: Context, val listCategories: ArrayList<String>) :
    RecyclerView.Adapter<CategoryAdapter.Vh>() {

    var categoryItemClickListener:CategoryItemClickListener?=null

    inner class Vh(var binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(category: String) {
            binding.topicName.text = category
            binding.topicName.setOnClickListener {
                if (categoryItemClickListener != null) {
                    categoryItemClickListener!!.onLick(category)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemCategoryBinding.inflate(LayoutInflater.from(context)))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(listCategories[position])
    }

    override fun getItemCount(): Int = listCategories.size

    interface CategoryItemClickListener{
        fun onLick(category:String)
    }
}