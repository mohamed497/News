package com.example.news.repository

import com.example.news.pojo.Article
import com.example.news.pojo.News
import com.example.news.repository.cache.NewsCacheRepository
import com.example.news.repository.remote.NewsRemoteRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

class NewsRepositoryImpl : NewsRepository {

    private val cacheRepo: NewsRepository = NewsCacheRepository()
    private val remoteRepo: NewsRepository = NewsRemoteRepository()
    override fun getAllNews(): Observable<News> {
        return remoteRepo.getAllNews()
            .flatMap { news ->
                saveNews(news.articles!!)
                return@flatMap Observable.just(news)
            }
    }


    override fun saveNews(article: List<Article>): Completable {
        return cacheRepo.saveNews(article)
    }

    override fun getSavedNews(): Observable<List<Article>> {
        return cacheRepo.getSavedNews()
    }
}