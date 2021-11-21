package com.example.news.repository.cache

import androidx.room.*
import com.example.news.pojo.Article
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable


@Dao
interface NewsDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewsIntoDB(news: List<Article>): Completable

    @Query("SELECT * FROM news_table")
    fun getAllNewsFromDB(): Observable<List<Article>>

}