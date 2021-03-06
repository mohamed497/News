package com.example.news.repository.cache

import com.example.news.pojo.Article
import com.example.news.pojo.News
import com.example.news.repository.NewsRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

class NewsCacheRepository(private val newsDao:NewsDatabase ) : NewsRepository {

    override fun getAllNews(): Observable<News> {
        throw UnsupportedOperationException("Cant Get Update Top News From Cache")
    }

    override fun saveNews(albums: List<Article>): Completable {
        return newsDao.newsDatabaseDao.insertNewsIntoDB(albums)
    }

    override fun getSavedNews(): Observable<List<Article>> {
        return newsDao.newsDatabaseDao.getAllNewsFromDB()
    }
}