package com.example.news.repository.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.news.pojo.Article

const val DB_VERSION = 1

@Database(entities = [Article::class], version = DB_VERSION, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract val newsDatabaseDao: NewsDatabaseDao
}