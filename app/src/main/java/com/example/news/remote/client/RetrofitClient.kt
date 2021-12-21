package com.example.news.remote.client

import com.example.news.base.NetworkConstants.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Mahmoud Gamal on 21/12/2021.
 */
object RetrofitClient {

    fun createRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    fun <T> createServiceClass(retrofit: Retrofit, classObj: Class<T>): T {
        return retrofit.create(classObj)
    }
}