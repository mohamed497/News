package com.example.news.pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SourceModel(
    val id: String,
    val name: String
) : Parcelable
