package com.example.news.remote.service

import com.example.news.domain.models.News
import com.example.news.remote.client.NetworkConstants.API_KEY
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface NewsApiService {

    @GET("top-headlines?country=us&apiKey=${API_KEY}")
    fun getNews(): Observable<News>

}
