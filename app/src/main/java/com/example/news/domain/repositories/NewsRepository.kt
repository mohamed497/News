package com.example.news.domain.repositories

import com.example.news.domain.models.Article
import com.example.news.domain.models.News
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface NewsRepository {

    fun getAllNewsFromRemote(): Observable<News>
    fun saveNews(articles: List<Article>): Completable
    fun getSavedNews(): Observable<List<Article>>
}