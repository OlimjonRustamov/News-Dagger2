package com.example.newsdagger2.main.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsdagger2.R
import com.example.newsdagger2.database.NewsEntity
import com.example.newsdagger2.databinding.ItemHomeNewsBinding
import com.squareup.picasso.Picasso

class HomeNewsAdapter(var context: Context, var news: ArrayList<NewsEntity>) :
    RecyclerView.Adapter<HomeNewsAdapter.Vh>() {

    var homeNewsItemClickListener:HomeNewsItemClickListener?=null

    inner class Vh(var binding: ItemHomeNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(article:NewsEntity) {
            Picasso.get().load(article.urlToImage).into(binding.homeNewsImage)
            binding.newsTitle.text = article.title.toString()
            if (article.isSaved == true) {
                binding.homeNewsBookmarkImage.setImageResource(R.drawable.ic_bookmark_selected)
            }
            binding.newsCategory.text = article.publishedAt.toString()
            binding.homeNewsImage.setOnClickListener {
                if (homeNewsItemClickListener != null) {
                    homeNewsItemClickListener!!.onItemClick(article)
                }
            }
            binding.homeNewsBookmarkImage.setOnClickListener {
                if (homeNewsItemClickListener != null) {
                    homeNewsItemClickListener!!.onBookmarkImageClick(article)
                }
                if (article.isSaved == true) {
                    binding.homeNewsBookmarkImage.setImageResource(R.drawable.ic_bookmark_selected)
                } else {
                    binding.homeNewsBookmarkImage.setImageResource(R.drawable.ic_bookmark_unselected)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemHomeNewsBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(news[position])
    }

    override fun getItemCount(): Int = news.size

    interface HomeNewsItemClickListener{
        fun onItemClick(article: NewsEntity)
        fun onBookmarkImageClick(article: NewsEntity)
    }
}