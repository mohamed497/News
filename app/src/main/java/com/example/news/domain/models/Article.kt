package com.example.news.domain.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.news.cache.database.CacheConstants.NEWS_TABLE_NAME
import kotlinx.android.parcel.Parcelize

@Entity(tableName = NEWS_TABLE_NAME)
@Parcelize
data class Article(
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    @PrimaryKey
    val publishedAt: String,
    val content: String? = null
) : Parcelable