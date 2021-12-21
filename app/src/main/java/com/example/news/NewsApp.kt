package com.example.news

import android.app.Application
import com.example.news.di.databaseModule
import com.example.news.di.repositoryModule
import com.example.news.di.serviceAPIModule
import com.example.news.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NewsApp:Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()

    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@NewsApp)
            modules(listOf(
                viewModelModule,
                repositoryModule,
                serviceAPIModule,
                databaseModule
            ))
        }
    }
}