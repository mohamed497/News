package com.example.news.ui.news.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.news.R
import com.example.news.base.GlobalConstants
import com.example.news.base.Resource
import com.example.news.pojo.Article
import com.example.news.pojo.News
import com.example.news.viewmodel.NewsViewModel
import com.example.news.ui.news.adapter.NewsAdapter
import kotlinx.android.synthetic.main.activity_news.*


class NewsActivity : AppCompatActivity() {
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private var articleSaveInstance: List<Article>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        initViewModel()
        initAdapter()
//        observeOnNews()
        checkInstanceState(savedInstanceState)
        viewModel.fetchNews()

    }

    private fun checkInstanceState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            getSavedInstanceState(savedInstanceState)

        } else {
            Log.d(NewsActivity::class.java.simpleName, " save instance = null")
            observeOnNews()
        }
    }

    private fun getSavedInstanceState(savedInstanceState: Bundle?) {
        articleSaveInstance =
            savedInstanceState?.getParcelable(GlobalConstants.SAVE_INSTANCE_NEWS)!!
        newsAdapter.setNews(articleSaveInstance ?: emptyList())
        Log.d(NewsActivity::class.java.simpleName, " save instance not equal null")
    }

    private fun initAdapter() {
        newsAdapter = NewsAdapter()
        newsRecyclerView.adapter = newsAdapter
    }

    private fun observeOnNews() {
        viewModel.observeOnNews(this, { newsResource ->
            when (newsResource.state) {
                Resource.Companion.State.LOADING -> {
                    progress.visibility = View.VISIBLE
                    errorText.visibility = View.GONE

                }
                Resource.Companion.State.SUCCESS -> {
                    progress.visibility = View.GONE
                    errorText.visibility = View.GONE

                    articleSaveInstance = newsResource.value
                    newsAdapter.setNews(newsResource.value ?: emptyList())
                }
                Resource.Companion.State.ERROR -> {
                    progress.visibility = View.GONE
                    errorText.visibility = View.VISIBLE
                    Log.d(NewsActivity::class.java.simpleName, newsResource.t.toString())
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
        outState.putParcelableArrayList(
            GlobalConstants.SAVE_INSTANCE_NEWS,
            ArrayList(viewModel.getArticle())
        )
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        articleSaveInstance = savedInstanceState.getParcelable(GlobalConstants.SAVE_INSTANCE_NEWS)
    }

}