package com.example.news.repository

import com.example.news.pojo.Article
import com.example.news.pojo.News
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface NewsRepository {

    fun getAllNews(): Observable<News>
    fun saveNews(albums: List<Article>): Completable
    fun getSavedNews(): Observable<List<Article>>

}