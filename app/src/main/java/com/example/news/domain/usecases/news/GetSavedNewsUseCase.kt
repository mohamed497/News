package com.example.news.domain.usecases.news

import com.example.news.cache.NewsCacheRepository
import com.example.news.domain.BaseObservableUseCase
import com.example.news.domain.models.Article
import com.example.news.domain.repositories.NewsRepository
import io.reactivex.rxjava3.core.Observable

class GetSavedNewsUseCase(private val newsCacheRepository: NewsCacheRepository) :
    BaseObservableUseCase<List<Article>, Any>() {

    override fun createObservable(params: Any?): Observable<List<Article>> {
        return newsCacheRepository.getSavedNews()
    }
}