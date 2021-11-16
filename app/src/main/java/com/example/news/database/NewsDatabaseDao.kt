package com.example.news.database

import androidx.paging.PagingSource
import androidx.room.*
import com.example.news.pojo.ArticlesModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable


@Dao
interface NewsDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewsIntoDB(news: List<ArticlesModel>): Completable

    @Query("SELECT * FROM news_table")
    fun getAllNewsFromDB(): Observable<List<ArticlesModel>>

    @Query("SELECT * FROM news_table ORDER BY newsId DESC")
    fun getAllPaged(): PagingSource<Int, ArticlesModel>


}