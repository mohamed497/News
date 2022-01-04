package com.example.news.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.news.cache.dao.NewsDatabaseDao
import com.example.news.domain.models.Article

const val DB_VERSION = 1

@Database(entities = [Article::class], version = DB_VERSION, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract val newsDatabaseDao: NewsDatabaseDao
}