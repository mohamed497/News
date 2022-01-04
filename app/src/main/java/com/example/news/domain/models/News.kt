package com.example.news.domain.models

import android.os.Parcelable
import com.example.news.domain.models.Article
import kotlinx.android.parcel.Parcelize

@Parcelize
data class News(
    val status: String?,
    val totalResults: String?,
    val articles: List<Article>
) : Parcelable

