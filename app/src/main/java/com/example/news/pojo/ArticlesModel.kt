package com.example.news.pojo

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "news_table")
@Parcelize
data class ArticlesModel(
//    @PrimaryKey(autoGenerate = true)
//    val newsId: Int? = null,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    @PrimaryKey
    val publishedAt: String,
    val content: String? = null
) : Parcelable
