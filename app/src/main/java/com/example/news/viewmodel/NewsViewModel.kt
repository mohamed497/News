package com.example.news.viewmodel

import android.os.Bundle
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

class NewsViewModel : ViewModel() {
    private val newsRepositoryImpl = NewsRepositoryImpl()
    private val compositeDisposable = CompositeDisposable()
    private val onSaveInstanceState: Bundle= Bundle()
    private val newsFromDatabase = MutableLiveData<Resource<List<Article>>>()
    private var news = News(null,null, null)
    fun observeOnNews(lifecycle: LifecycleOwner, news: Observer<Resource<List<Article>>>) {
        newsFromDatabase.observe(lifecycle, news)
    }

    private fun setNews(news: News) : News{
        this.news = news
        return news
    }
    fun getNews(): News{
        return news
    }

    fun fetchNews() {
        newsRepositoryImpl.getAllNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ news ->
//                onSaveInstanceState.putParcelable(GlobalConstants.SAVE_INSTANCE_NEWS, news)
                setNews(news)
                insertNewsIntoDB(news.articles!!)
            }, { throwable ->
                Log.d(GlobalConstants.NEWS_VIEW_MODEL_ERROR, throwable.toString())
            }, {
                Log.d(GlobalConstants.NEWS_VIEW_MODEL_SUCCESS, "Remote_Complete")

            })
    }



    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun getNewsFromDB() {
        newsFromDatabase.value = Resource.loading()
        newsRepositoryImpl.getSavedNews().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ news ->
                newsFromDatabase.postValue(Resource.success(news.take(5), news.size, news))
            },
                { throwable ->
                    Log.d(GlobalConstants.NEWS_VIEW_MODEL_ERROR, throwable.toString())
                    newsFromDatabase.value = Resource.error(throwable)
                },
                {
                    Log.d(GlobalConstants.NEWS_VIEW_MODEL_SUCCESS, "Get Data Complete")
                })
    }



    private fun insertNewsIntoDB(articlesModel: List<Article>) {
        newsRepositoryImpl.saveNews(articlesModel).subscribeOn(Schedulers.io())
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