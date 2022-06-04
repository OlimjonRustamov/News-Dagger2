package com.example.newsdagger2.di.component

import com.example.newsdagger2.MainActivity
import com.example.newsdagger2.di.module.DatabaseModule
import com.example.newsdagger2.di.module.NetworkModule
import com.example.newsdagger2.main.NewsFragment
import com.example.newsdagger2.main.bookmark.BookmarkFragment
import com.example.newsdagger2.main.home.HomeFragment
import com.example.newsdagger2.main.home.NewsListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class])
interface AppComponent {
    fun inject(homeFragment: HomeFragment)
    fun inject(newsListFragment: NewsListFragment)
    fun inject(bookmarkFragment: BookmarkFragment)
    fun inject(newsFragment: NewsFragment)
}