package com.example.news.remote.services

import com.example.news.base.GlobalConstants
import com.example.news.pojo.News
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface NewsApiService {

    @GET("top-headlines?country=us&apiKey=${GlobalConstants.API_KEY}")
    fun getNews(): Observable<News>

}
