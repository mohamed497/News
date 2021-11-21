package com.example.news.pojo

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.news.base.GlobalConstants
import kotlinx.android.parcel.Parcelize

@Entity(tableName = GlobalConstants.NEWS_TABLE_NAME)
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