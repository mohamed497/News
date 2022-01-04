package com.example.news.domain.usecases.news

import com.example.news.domain.BaseObservableUseCase
import com.example.news.domain.models.News
import com.example.news.remote.NewsRemoteRepository
import com.example.news.view.utils.debug
import io.reactivex.rxjava3.core.Observable

class GetAllNewsFromRemoteUseCase(
    private val newsRemoteRepository: NewsRemoteRepository,
    private val saveNewsUseCase: SaveNewsUseCase
) :
    BaseObservableUseCase<News, Any>() {
    override fun createObservable(params: Any?): Observable<News> {
        return newsRemoteRepository.getAllNewsFromRemote()
            .flatMap { news ->
                saveNewsUseCase.createCompletable(SaveNewsUseCase.Params.createParams(news.articles))
                debug(news.articles.size.toString())
                return@flatMap Observable.just(news)
            }
    }
}