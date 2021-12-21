package com.example.news.remote

import com.example.news.remote.client.RetrofitClient
import com.example.news.remote.services.NewsApiService
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * @author Mahmoud Gamal on 21/12/2021.
 */
val networkKoinModule = module {
    single<Retrofit> { RetrofitClient.createRetrofitInstance() }

    single<NewsApiService> { RetrofitClient.createServiceClass(get(), NewsApiService::class.java) }


}
