package com.example.newsdagger2.App

import android.app.Application
import com.example.newsdagger2.cache.Cache
import com.example.newsdagger2.di.component.AppComponent
import com.example.newsdagger2.di.component.DaggerAppComponent
import com.example.newsdagger2.di.module.DatabaseModule

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }
    override fun onCreate() {
        super.onCreate()
        Cache.init(this)

        appComponent = DaggerAppComponent.builder()
            .databaseModule(DatabaseModule(this))
            .build()
    }
}