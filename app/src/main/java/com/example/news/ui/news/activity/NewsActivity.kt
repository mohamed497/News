package com.example.news.ui.news.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.news.R
import com.example.news.base.GlobalConstants
import com.example.news.base.Resource
import com.example.news.pojo.News
import com.example.news.viewmodel.NewsViewModel
import com.example.news.ui.news.adapter.NewsAdapter
import kotlinx.android.synthetic.main.activity_news.*


class NewsActivity : AppCompatActivity() {
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private var news = News(null, null, null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        initViewModel()
        getNewsData()
        if (savedInstanceState != null) {
            initAdapter()
            news = savedInstanceState.getParcelable(GlobalConstants.SAVE_INSTANCE_NEWS)!!
            newsAdapter.setNews(news.articles!!)
            Log.d(NewsActivity::class.java.simpleName, " save instance not equal null")

        }else{
            Log.d(NewsActivity::class.java.simpleName, " save instance = null")
            observeOnNews()
        }

    }

    private fun getNewsData() {
        viewModel.fetchNews()
        viewModel.getNewsFromDB()
    }

    private fun initAdapter() {
        newsAdapter = NewsAdapter()
        newsRecyclerView.adapter = newsAdapter
    }

    private fun observeOnNews() {
        initAdapter()
        viewModel.observeOnNews(this, { newsResource ->
            when (newsResource.state) {
                Resource.Companion.State.LOADING -> {
                    progress.visibility = View.VISIBLE
                    errorText.visibility = View.GONE

                }
                Resource.Companion.State.SUCCESS -> {
                    progress.visibility = View.GONE
                    errorText.visibility = View.GONE

                    Log.d(NewsActivity::class.java.simpleName, newsResource.size.toString())
                    newsAdapter.setNews(newsResource.allData!!)

                }
                Resource.Companion.State.ERROR -> {
                    progress.visibility = View.GONE
                    errorText.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun initViewModel() {
        viewModel =
            ViewModelProvider(this)
                .get(NewsViewModel::class.java)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(GlobalConstants.SAVE_INSTANCE_NEWS, viewModel.getNews())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        news = savedInstanceState.getParcelable(GlobalConstants.SAVE_INSTANCE_NEWS)!!
    }
}