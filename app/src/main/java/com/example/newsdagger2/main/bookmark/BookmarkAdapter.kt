package com.example.newsdagger2.main.bookmark

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsdagger2.database.NewsEntity
import com.example.newsdagger2.databinding.ItemBookmarkBinding
import com.example.newsdagger2.di.model.Article
import com.squareup.picasso.Picasso

class BookmarkAdapter(val context: Context, val list: ArrayList<NewsEntity>) :
    RecyclerView.Adapter<BookmarkAdapter.Vh>() {

    var bookMarkItemClickListener:BookMarkItemClickListener?=null

    inner class Vh(var binding: ItemBookmarkBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(article: NewsEntity) {
            binding.bookmarkCategory.text = article.publishedAt
            binding.bookmarkTitle.text = article.title
            Picasso.get().load(article.urlToImage).into(binding.bookmarkImage)
            binding.root.setOnClickListener {
                if (bookMarkItemClickListener != null) {
                    bookMarkItemClickListener!!.onClick(article)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemBookmarkBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface BookMarkItemClickListener{
        fun onClick(article: NewsEntity)
    }
}