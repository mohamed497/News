package com.example.news.repository

import androidx.paging.PagingSource
import com.example.news.database.NewsDatabaseDao
import com.example.news.netowrk.NewsApiService
import com.example.news.pojo.ArticlesModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

class NewsRepo(
    private val retrofitService: NewsApiService,
    private val newsDatabaseDao: NewsDatabaseDao
) {

    fun getAllNews() = retrofitService.getNews()

    fun getNewsFromDB(): Observable<List<ArticlesModel>> {
        return newsDatabaseDao.getAllNewsFromDB()
    }

    fun loadAllNewsByPage(limit: Int, offset: Int): Observable<List<ArticlesModel>> {
        return newsDatabaseDao.loadAllNewsByPage(limit, offset)
    }

    fun insert(articlesModel: List<ArticlesModel>): Completable {
        return newsDatabaseDao.insertNewsIntoDB(articlesModel)
    }



    fun getAllPaged(): PagingSource<Int, ArticlesModel> {
        return newsDatabaseDao.getAllPaged()
    }
}