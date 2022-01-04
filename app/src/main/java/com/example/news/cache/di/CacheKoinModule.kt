package com.example.news.cache.di

import android.app.Application
import androidx.room.Room
import com.example.news.cache.database.CacheConstants
import com.example.news.cache.database.NewsDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    fun getDatabaseInstance(application: Application): NewsDatabase {
        return Room.databaseBuilder(
            application,
            NewsDatabase::class.java, CacheConstants.DATABASE_NAME
        )
            .build()
    }
    single { getDatabaseInstance(androidApplication()) }
}