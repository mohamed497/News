package com.example.news.remote

import com.example.news.pojo.Article
import com.example.news.pojo.News
import com.example.news.repository.NewsRepository
import com.example.news.remote.services.NewsApiService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

class NewsRemoteRepository( private val newsService: NewsApiService) : NewsRepository {

    override fun getAllNews(): Observable<News> {
        return newsService.getNews()
    }

    override fun saveNews(albums: List<Article>): Completable {
        throw UnsupportedOperationException("You can not save news in Remote layer")
    }

    override fun getSavedNews(): Observable<List<Article>> {
        throw UnsupportedOperationException("You can not get saved news in Remote layer")
    }
}