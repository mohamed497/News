package com.example.news.utils

import android.util.Log

/**
 * @author Mahmoud Gamal on 21/12/2021.
 */

fun Any.debug(message: Any) {
    Log.d(this::class.java.simpleName, message.toString())
}

fun Any.error(message: String, throwable: Throwable?) {
    Log.e(this::class.java.simpleName, message, throwable)
}