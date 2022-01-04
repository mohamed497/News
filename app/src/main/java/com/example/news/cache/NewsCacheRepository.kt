package com.example.news.cache

import com.example.news.cache.database.NewsDatabase
import com.example.news.domain.models.Article
import com.example.news.domain.models.News
import com.example.news.domain.repositories.NewsRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

class NewsCacheRepository(private val newsDao: NewsDatabase) : NewsRepository {

    override fun getAllNewsFromRemote(): Observable<News> {
        throw UnsupportedOperationException("Cant Get Update Top News From Cache")
    }

    override fun saveNews(albums: List<Article>): Completable {
        return newsDao.newsDatabaseDao.insertNewsIntoDB(albums)
    }

    override fun getSavedNews(): Observable<List<Article>> {
        return newsDao.newsDatabaseDao.getAllNewsFromDB()
    }
}