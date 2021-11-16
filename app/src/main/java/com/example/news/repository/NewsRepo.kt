package com.example.news.repository

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



    fun insert(articlesModel: List<ArticlesModel>): Completable {
        return newsDatabaseDao.insertNewsIntoDB(articlesModel)
    }




}