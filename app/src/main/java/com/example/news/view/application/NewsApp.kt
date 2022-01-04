package com.example.news.view.application

import android.app.Application
import com.example.news.cache.di.databaseModule
import com.example.news.presentation.di.repositoryModule
import com.example.news.presentation.di.useCaseModule
import com.example.news.presentation.di.viewModelModule
import com.example.news.remote.di.networkKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NewsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()

    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@NewsApp)
            modules(
                listOf(
                    viewModelModule,
                    useCaseModule,
                    repositoryModule,
                    networkKoinModule,
                    databaseModule
                )
            )
        }
    }
}