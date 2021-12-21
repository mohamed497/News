package com.example.news.repository.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.news.pojo.Article
import com.example.news.repository.cache.dao.NewsDatabaseDao

const val DB_VERSION = 1

@Database(entities = [Article::class], version = DB_VERSION, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract val newsDatabaseDao: NewsDatabaseDao
}