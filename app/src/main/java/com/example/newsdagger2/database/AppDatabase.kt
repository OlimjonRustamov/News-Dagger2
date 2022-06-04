package com.example.newsdagger2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsdagger2.di.model.Article

@Database(entities = [NewsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
}