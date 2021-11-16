package com.example.news.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news.base.GlobalConstants
import com.example.news.pojo.ArticlesModel
import com.example.news.repository.NewsRepo
import com.example.news.resource.Resource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class NewsViewModel(private val newsRepo: NewsRepo) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _newsDB = MutableLiveData<Resource<List<ArticlesModel>>>()
    val newsDB: LiveData<Resource<List<ArticlesModel>>>
        get() = _newsDB

    fun getNews() {
        fetchNews().subscribe(
            { newsModel ->
                insertNewsIntoDB(newsModel)
            },
            { throwable ->
                Log.d(GlobalConstants.NEWS_VIEW_MODEL_ERROR, throwable.toString())
            },
            { Log.d(GlobalConstants.NEWS_VIEW_MODEL_SUCCESS, "Complete") }
        ).let { disposable ->
            compositeDisposable.add(disposable)
        }
    }

    private fun fetchNews(): Observable<List<ArticlesModel>> {
        return newsRepo.getAllNews()
            .map { news ->
                return@map (news.articles)
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun getNewsFromDB() {
        _newsDB.value = Resource.loading()
        newsRepo.getNewsFromDB().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ news ->
                _newsDB.value = Resource.success(news, news.size)
            },
                { throwable ->
                    Log.d(GlobalConstants.NEWS_VIEW_MODEL_ERROR, throwable.toString())
                    _newsDB.value = Resource.error(throwable)
                },
                {
                    Log.d(GlobalConstants.NEWS_VIEW_MODEL_SUCCESS, "Complete")
                })
    }


    private fun insertNewsIntoDB(articlesModel: List<ArticlesModel>) {
        newsRepo.insert(articlesModel).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(GlobalConstants.NEWS_VIEW_MODEL_SUCCESS, "Complete")
            }, { throwable ->
                Log.d(GlobalConstants.NEWS_VIEW_MODEL_ERROR, throwable.toString())

            }).let { disposable ->
                compositeDisposable.add(disposable)
            }
    }

}