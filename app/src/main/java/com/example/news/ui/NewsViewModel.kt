package com.example.news.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.example.news.netowrk.Constants
import com.example.news.pojo.ArticlesModel
import com.example.news.pojo.NewsResponse
import com.example.news.repository.NewsRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class NewsViewModel(private val newsRepo: NewsRepo) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
//    private val _news = MutableLiveData<List<ArticlesModel>>()
//    val news: LiveData<List<ArticlesModel>>
//        get() = _news

//    private val _newsDB = MutableLiveData<List<ArticlesModel>>()
//    val newsDB: LiveData<List<ArticlesModel>>
//        get() = _newsDB

    fun getNews() {
        fetchNews().subscribe(
            { newsModel ->
//                _news.value = newsModel
                insertNewsIntoDB(newsModel)
            },
            { Log.d(Constants.NEWS_VIEW_MODEL_ERROR, it.toString()) },
            { Log.d(Constants.NEWS_VIEW_MODEL_SUCCESS, "Complete") }
        ).let {
            compositeDisposable.add(it)
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
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }

//    fun getNewsFromDB() {
//        newsRepo.getNewsFromDB().subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({ news ->
//                _newsDB.postValue(news)
//            },
//                {
//                    Log.d("zxc_error_DB", it.toString())
//                },
//                {
//                    Log.d("zxc_DB", "Complete")
//                })
//    }

//    fun loadAllNewsByPage(limit: Int, offset: Int) {
//        newsRepo.loadAllNewsByPage(limit, offset).subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({ news ->
//                _newsDB.postValue(news)
//            }, {
//                Log.d("zxc_error_DB", it.toString())
//
//            }, {
//                Log.d("zxc_DB", "Complete")
//
//            })
//    }

    private fun insertNewsIntoDB(articlesModel: List<ArticlesModel>) {
        newsRepo.insert(articlesModel).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(Constants.NEWS_VIEW_MODEL_SUCCESS, "Complete")
            }, {
                Log.d(Constants.NEWS_VIEW_MODEL_ERROR, it.toString())

            }).let {
                compositeDisposable.add(it)
            }
    }


    val items = Pager(
        PagingConfig(
            pageSize = 5,
            enablePlaceholders = true,
            maxSize = 200
        )
    ) {
        newsRepo.getAllPaged()
    }.flow


}