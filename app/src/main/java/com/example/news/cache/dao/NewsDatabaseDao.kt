package com.example.news.cache.dao

import androidx.room.*
import com.example.news.cache.database.CacheConstants.NEWS_TABLE_NAME
import com.example.news.domain.models.Article
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable


@Dao
interface NewsDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewsIntoDB(news: List<Article>): Completable

    @Query("SELECT * FROM $NEWS_TABLE_NAME")
    fun getAllNewsFromDB(): Observable<List<Article>>

}