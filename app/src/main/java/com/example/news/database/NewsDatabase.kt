package com.example.news.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.news.pojo.ArticlesModel

const val DB_VERSION = 1

@Database(entities = [ArticlesModel::class], version = DB_VERSION, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract val newsDatabaseDao: NewsDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: NewsDatabase? = null
        fun getInstance(context: Context): NewsDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NewsDatabase::class.java, "news_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}