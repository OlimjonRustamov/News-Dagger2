package com.example.newsdagger2.database

import androidx.room.*

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNews(newsEntity: NewsEntity):Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllNews(newsEntity: List<NewsEntity>)

    @Delete
    fun deleteNews(newsEntity: NewsEntity)

    @Update
    fun updateNews(newsEntity: NewsEntity)

    @Query("select * from newsentity")
    fun getAllNews():List<NewsEntity>

    @Query("select MAX(id) from newsentity")
    fun getLastInsertedID():Int

    @Query("select * from newsentity where title=:title")
    fun getItem(title:String):List<NewsEntity>

    @Query("select * from newsentity where isSaved=1")
    fun getAllSavedNews():List<NewsEntity>


}