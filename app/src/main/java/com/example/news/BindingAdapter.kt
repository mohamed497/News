package com.example.news

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.news.pojo.ArticlesModel
import com.example.news.ui.NewsAdapter


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<ArticlesModel>?){
    val adapter= recyclerView.adapter as? NewsAdapter
    adapter?.submitList(data)
}


