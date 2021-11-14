package com.example.news.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.news.repository.NewsRepo

class NewsViewModelFactory(private val newsRepo: NewsRepo, ) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(newsRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}