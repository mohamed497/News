package com.example.news.domain

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class BaseObservableUseCase<T, P> {

    fun getObservable(params: P? = null): Observable<T> =
        createObservable(params = params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    abstract fun createObservable(params: P? = null): Observable<T>
}