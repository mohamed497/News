package com.example.news.repository.remote

import com.example.news.base.GlobalConstants
import com.example.news.pojo.News
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
    .baseUrl(GlobalConstants.BASE_URL)
    .build()

interface NewsApiService {

    @GET("top-headlines?country=us&apiKey=${GlobalConstants.API_KEY}")
    fun getNews(): Observable<News>

}

object NewsApi {
    val retrofitService: NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }
}