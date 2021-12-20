package com.example.news.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.news.base.GlobalConstants
import com.example.news.repository.NewsRepository
import com.example.news.repository.NewsRepositoryImpl
import com.example.news.repository.cache.NewsCacheRepository
import com.example.news.repository.cache.NewsDatabase
import com.example.news.repository.remote.NewsApiService
import com.example.news.repository.remote.NewsRemoteRepository
import com.example.news.viewmodel.NewsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel { NewsViewModel(newsRepositoryImpl = get()) }
}

val repositoryModule = module {
    single { NewsRemoteRepository(newsService = get()) }
    single { NewsCacheRepository(newsDao = get()) }
    single { NewsRepositoryImpl(cacheRepo = get(), remoteRepo = get()) }

}
val serviceAPIModule = module {
    fun getRetrofitBuilder(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(GlobalConstants.BASE_URL)
            .build()
    }
    single { getRetrofitBuilder() }
    fun getServiceAPIInstance(retrofit: Retrofit): NewsApiService {
        return retrofit.create(NewsApiService::class.java)
    }
    single { getServiceAPIInstance(retrofit = get()) }
}
val databaseModule = module {
    fun getDatabaseInstance(application: Application): NewsDatabase {
            return Room.databaseBuilder(
                application,
                NewsDatabase::class.java, GlobalConstants.DATABASE_NAME
            )
                .build()
    }
    single { getDatabaseInstance(androidApplication()) }
}