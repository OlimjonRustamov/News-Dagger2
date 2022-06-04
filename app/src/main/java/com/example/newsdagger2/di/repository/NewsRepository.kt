package com.example.newsdagger2.di.repository

import com.example.newsdagger2.database.NewsDao
import com.example.newsdagger2.database.NewsEntity
import com.example.newsdagger2.di.model.Article
import com.example.newsdagger2.di.module.ApiService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(val apiService: ApiService, val dao:NewsDao) {
    suspend fun getNews(query: String, from: String, sortBy: String) =
       apiService.getNews(query, from, sortBy, "a2c1e601d3c7407daa6c42fd38b987c4")

    fun insertAllNews(list:List<NewsEntity>) = dao.insertAllNews(list)
    fun insertNews(newsEntity: NewsEntity):Long = dao.insertNews(newsEntity)

    fun getAllSavedNews() = dao.getAllSavedNews()

    fun updateArticle(news:NewsEntity) = dao.updateNews(news)

    fun getLastInsertedID():Int = dao.getLastInsertedID()

    fun getItem(title:String):List<NewsEntity> = dao.getItem(title)
}