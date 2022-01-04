package com.example.news.domain.usecases.news

import com.example.news.cache.NewsCacheRepository
import com.example.news.domain.BaseCompletableUseCase
import com.example.news.domain.models.Article
import com.example.news.domain.repositories.NewsRepository
import io.reactivex.rxjava3.core.Completable

class SaveNewsUseCase(private val newsCacheRepository: NewsCacheRepository) :
    BaseCompletableUseCase<SaveNewsUseCase.Params>() {

    override fun createCompletable(params: Params?): Completable =
        if (params != null)
            newsCacheRepository
                .saveNews(params.articles)
        else
            throw IllegalArgumentException("params can not be null")


    data class Params(val articles: List<Article>) {
        companion object {
            fun createParams(articles: List<Article>): Params =
                Params(articles = articles)
        }
    }


}