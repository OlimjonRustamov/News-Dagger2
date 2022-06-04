package com.example.newsdagger2.di.model

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)