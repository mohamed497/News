package com.example.news.repository

import android.util.Log
import com.example.news.pojo.Article
import com.example.news.pojo.News
import com.example.news.repository.cache.NewsCacheRepository
import com.example.news.repository.remote.NewsRemoteRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource

class NewsRepositoryImpl : NewsRepository {

    private val cacheRepo: NewsRepository = NewsCacheRepository()
    private val remoteRepo: NewsRepository = NewsRemoteRepository()

    //    override fun getAllNews(): Observable<News> {
//        var newsList: News? = null
//        return remoteRepo.getAllNews()
//            .flatMap {
//                news ->
//                newsList = news
//                Log.d(NewsRepositoryImpl::class.java.simpleName,news.articles.size.toString())
//                return@flatMap saveNews(news.articles)
//                    .andThen(ObservableSource { observer ->
//                        observer.onNext(newsList)
//                    })
//            }
//    }
    override fun getAllNews(): Observable<News> {
        return remoteRepo.getAllNews()
            .flatMap { news ->
                saveNews(news.articles)
                Log.d(NewsRepositoryImpl::class.java.simpleName, news.articles.size.toString())
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