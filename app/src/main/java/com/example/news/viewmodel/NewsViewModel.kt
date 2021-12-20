package com.example.news.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.news.base.GlobalConstants
import com.example.news.pojo.Article
import com.example.news.base.Resource
import com.example.news.pojo.News
import com.example.news.repository.NewsRepositoryImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class NewsViewModel(private val newsRepositoryImpl: NewsRepositoryImpl) : ViewModel() {
    private val newsList = MutableLiveData<Resource<List<Article>>>()
    fun observeOnNews(lifecycle: LifecycleOwner, news: Observer<Resource<List<Article>>>) {
        newsList.observe(lifecycle, news)
    }

    fun fetchNews() {
        newsList.value = Resource.loading()
        newsRepositoryImpl.getSavedNews().flatMap {
            return@flatMap newsRepositoryImpl.getAllNews()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ news ->
                Log.d(NewsViewModel::class.java.simpleName, news.articles.size.toString())
                newsList.value = Resource.success(news.articles)
            },
                { throwable ->
                    Log.d(GlobalConstants.NEWS_VIEW_MODEL_ERROR, throwable.toString())
                    newsList.value = Resource.error(throwable)
                },
                {
                    Log.d(GlobalConstants.NEWS_VIEW_MODEL_SUCCESS, "Get Data Complete")
                })
    }

    fun getArticle(): List<Article> = newsList.value?.value ?: emptyList()

    private val compositeDisposable = CompositeDisposable()
    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}