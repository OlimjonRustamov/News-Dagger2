package com.example.newsdagger2.di.module

import android.content.Context
import androidx.room.Room
import com.example.newsdagger2.BuildConfig
import com.example.newsdagger2.database.AppDatabase
import com.example.newsdagger2.database.NewsDao
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DatabaseModule(var context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideAppDatabase(): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "my_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): NewsDao = appDatabase.newsDao()
}