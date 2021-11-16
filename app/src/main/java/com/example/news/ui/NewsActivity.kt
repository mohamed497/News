package com.example.news.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.news.R
import com.example.news.database.NewsDatabase
import com.example.news.database.NewsDatabaseDao
import com.example.news.netowrk.NewsApi
import com.example.news.pojo.ArticlesModel
import com.example.news.repository.NewsRepo
import com.example.news.resource.Resource
import kotlinx.android.synthetic.main.activity_news.*


class NewsActivity : AppCompatActivity() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var dao: NewsDatabaseDao
    lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        initDatabaseDao()
        initViewModel()

        viewModelData()
        viewModel.newsDB.observe(this, { newsResource ->
            when (newsResource.state) {
                Resource.Companion.State.LOADING -> {

                }
                Resource.Companion.State.SUCCESS -> {
                    Log.d(NewsActivity::class.java.simpleName, newsResource.size.toString())
                    initAdapter(newsResource.value!!)
                    initAdapter(newsResource.allData!!)
                }
                Resource.Companion.State.ERROR -> {

                }
            }
        })

    }

    private fun viewModelData() {
        viewModel.getNews()
        viewModel.getNewsFromDB()
    }

    private fun initAdapter(news: List<ArticlesModel>) {
        newsAdapter = NewsAdapter(news)
        newsRecyclerView.adapter = newsAdapter
        newsAdapter.notifyDataSetChanged()
    }

    private fun initViewModel() {
        viewModel =
            ViewModelProvider(this, NewsViewModelFactory(NewsRepo(NewsApi.retrofitService, dao)))
                .get(NewsViewModel::class.java)
    }

    private fun initDatabaseDao() {
        dao = NewsDatabase.getInstance(application).newsDatabaseDao
    }


}