package com.example.newsdagger2.di.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsdagger2.database.AppDatabase
import com.example.newsdagger2.database.NewsEntity
import com.example.newsdagger2.di.model.NewsResponse
import com.example.newsdagger2.di.repository.NewsRepository
import com.example.newsdagger2.utils.NetworkHelper
import com.example.newsdagger2.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    private val newsResponse =
        MutableLiveData<Resource<ArrayList<NewsEntity>>>(Resource.loading(null))

    fun getNews(query: String, from: String, sortBy: String): LiveData<Resource<ArrayList<NewsEntity>>> {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch {
                try {
                    val news = newsRepository.getNews(query, from, sortBy)
                    val list = ArrayList<NewsEntity>()
                    launch(Dispatchers.IO) {
                        news.articles.forEach {
                            val article = NewsEntity(
                                0, it.author, it.content, it.description, it.publishedAt,
                                it.title, it.url, it.urlToImage, false
                            )
                            val res = newsRepository.insertNews(article)
                            val newsEntity = newsRepository.getItem(it.title!!)
                            list.add(newsEntity[0])
                        }
                        launch(Dispatchers.Main){
                            newsResponse.value = Resource.success(list)
                        }
//                        newsRepository.insertAllNews(list)
                    }

                } catch (e: Exception) {
                    newsResponse.value = Resource.error(e.message ?: "", null)
                }
            }
        } else {
            newsResponse.value = Resource.error("Internet not connected", null)
        }

        return newsResponse
    }
}