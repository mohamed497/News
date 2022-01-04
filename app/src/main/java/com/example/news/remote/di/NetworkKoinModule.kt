package com.example.news.remote.di

import com.example.news.remote.client.RetrofitClient
import com.example.news.remote.service.NewsApiService
import org.koin.dsl.module
import retrofit2.Retrofit

val networkKoinModule = module {
    single<Retrofit> { RetrofitClient.createRetrofitInstance() }
    single<NewsApiService> { RetrofitClient.createServiceClass(get(), NewsApiService::class.java) }

}