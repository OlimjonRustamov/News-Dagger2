package com.example.newsdagger2.di.module

import android.content.Context
import android.provider.SyncStateContract
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideBaseUrl(): String = "https://newsapi.org/v2/"


    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

//    @Provides
//    @Singleton
//    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
//        val httpLoggingInterceptor = HttpLoggingInterceptor()
//        if (BuildConfig.DEBUG) {
//            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        } else {
//            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
//        }
//        return httpLoggingInterceptor
//    }

//    @Provides
//    @Singleton
//    fun provideOkhttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
//        val okHttpClient = OkHttpClient.Builder()
//        okHttpClient.addInterceptor(httpLoggingInterceptor)
//        return okHttpClient.build()
//    }

    @Provides
    @Singleton
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory, baseUrl: String): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(gsonConverterFactory).build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

}