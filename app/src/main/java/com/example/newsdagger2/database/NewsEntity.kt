package com.example.newsdagger2.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(indices = [Index(value = ["title"],
    unique = true)])
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    val author: String?="",
    val content: String?="",
    val description: String?="",
    val publishedAt: String?="",
    val title: String?="",
    val url: String?="",
    val urlToImage: String?="",
    var isSaved:Boolean?=false
):Serializable