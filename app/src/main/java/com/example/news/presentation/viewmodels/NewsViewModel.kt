package com.example.news.presentation.viewmodels

import androidx.lifecycle.*
import com.example.news.domain.models.Article
import com.example.news.presentation.base.Resource
import com.example.news.domain.usecases.news.GetAllNewsFromRemoteUseCase
import com.example.news.domain.usecases.news.GetSavedNewsUseCase
import com.example.news.view.utils.debug
import com.example.news.view.utils.error
import io.reactivex.rxjava3.disposables.CompositeDisposable

class NewsViewModel(
    private val getSavedNewsUseCase: GetSavedNewsUseCase,
    private val getAllNewsFromRemoteUseCase: GetAllNewsFromRemoteUseCase
) : ViewModel() {

    fun fetchNews() {
        newsList.value = Resource.loading()
        getSavedNewsUseCase.getObservable().flatMap {
            return@flatMap getAllNewsFromRemoteUseCase.getObservable()
        }.subscribe({ news ->
            debug(news.articles.size.toString())
            newsList.value = Resource.success(news.articles)
        },
            { throwable ->
                error(throwable.toString(), throwable)
                newsList.value = Resource.error(throwable)
            },
            {
                debug("Get Data Complete")
            })
    }

    private val newsList = MutableLiveData<Resource<List<Article>>>()
    fun observeOnNews(lifecycle: LifecycleOwner, news: Observer<Resource<List<Article>>>) {
        newsList.observe(lifecycle, news)
    }

    fun getArticle(): List<Article> = newsList.value?.value ?: emptyList()

    private val compositeDisposable = CompositeDisposable()
    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}