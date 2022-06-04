package com.example.newsdagger2.start.select_topic

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsdagger2.R
import com.example.newsdagger2.databinding.ItemTopicBinding

class SelectTopicAdapter(var context: Context, var topics: ArrayList<String>) :
    RecyclerView.Adapter<SelectTopicAdapter.Vh>() {

    var myItemClickListener:MyItemClickListener?=null

    inner class Vh(var binding: ItemTopicBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(topic: String, position: Int) {
            binding.topicName.isSelected = true
            binding.topicName.text = topic
            binding.topicName.setOnClickListener {
                it.isSelected = !it.isSelected
                if (myItemClickListener != null) {
                    myItemClickListener!!.itemClick(position, it.isSelected)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemTopicBinding.inflate(LayoutInflater.from(context)))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(topics[position], position)
    }

    override fun getItemCount(): Int = topics.size

    interface MyItemClickListener{
        fun itemClick(position: Int, isSelected:Boolean)
    }
}