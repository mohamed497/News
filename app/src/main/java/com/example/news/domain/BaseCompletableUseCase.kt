package com.example.news.domain

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers


abstract class BaseCompletableUseCase<P> {

    fun getCompletable(params: P? = null): Completable =
        createCompletable(params = params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    abstract fun createCompletable(params: P? = null): Completable
}