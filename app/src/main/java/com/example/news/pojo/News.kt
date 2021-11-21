package com.example.news.pojo

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class News(
    val status: String?,
    val totalResults: String?,
    val articles: List<Article>?
) : Parcelable

