package com.example.news.pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsResponse(
    val status: String,
    val totalResults: String,
    val articles: List<ArticlesModel>
) : Parcelable