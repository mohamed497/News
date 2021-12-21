package com.example.news.repository.cache.di

import android.app.Application
import androidx.room.Room
import com.example.news.repository.cache.database.CacheConstants.DATABASE_NAME
import com.example.news.repository.cache.database.NewsDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    fun getDatabaseInstance(application: Application): NewsDatabase {
        return Room.databaseBuilder(
            application,
            NewsDatabase::class.java, DATABASE_NAME
        )
            .build()
    }
    single { getDatabaseInstance(androidApplication()) }
}