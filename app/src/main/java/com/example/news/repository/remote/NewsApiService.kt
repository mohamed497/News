package com.example.news.repository.remote

import com.example.news.base.GlobalConstants
import com.example.news.pojo.News
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface NewsApiService {

    @GET("top-headlines?country=us&apiKey=${GlobalConstants.API_KEY}")
    fun getNews(): Observable<News>

}
