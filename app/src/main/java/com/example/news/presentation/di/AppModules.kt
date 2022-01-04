package com.example.news.presentation.di

import com.example.news.cache.NewsCacheRepository
import com.example.news.domain.usecases.news.GetAllNewsFromRemoteUseCase
import com.example.news.domain.usecases.news.GetSavedNewsUseCase
import com.example.news.domain.usecases.news.SaveNewsUseCase
import com.example.news.remote.NewsRemoteRepository
import com.example.news.presentation.viewmodels.NewsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { NewsViewModel(getSavedNewsUseCase = get(), getAllNewsFromRemoteUseCase = get()) }
}

val repositoryModule = module {
    single { NewsRemoteRepository(newsService = get()) }
    single { NewsCacheRepository(newsDao = get()) }
}

val useCaseModule = module {
    single { SaveNewsUseCase(newsCacheRepository = get()) }
    single { GetSavedNewsUseCase(newsCacheRepository = get()) }
    single { GetAllNewsFromRemoteUseCase(newsRemoteRepository = get(), saveNewsUseCase = get()) }
}

