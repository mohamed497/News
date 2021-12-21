package com.example.news.ui.news.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.news.R
import com.example.news.base.GlobalConstants
import com.example.news.base.Resource
import com.example.news.pojo.Article
import com.example.news.ui.news.adapter.NewsAdapter
import com.example.news.utils.toGone
import com.example.news.utils.toVisible
import com.example.news.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.activity_news.*
import org.koin.android.viewmodel.ext.android.viewModel

private const val SAVE_INSTANCE_NEWS = "News"

class NewsActivity : AppCompatActivity() {
    private val viewModel: NewsViewModel by viewModel()
    private lateinit var newsAdapter: NewsAdapter
    private var articleSaveInstance: List<Article>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        initAdapter()
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
                    progress.toVisible()
                    errorText.toGone()
                }
                Resource.Companion.State.SUCCESS -> {
                    progress.toGone()
                    errorText.toGone()
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


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(
            SAVE_INSTANCE_NEWS,
            ArrayList(viewModel.getArticle())
        )
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        articleSaveInstance = savedInstanceState.getParcelable(SAVE_INSTANCE_NEWS)
    }

}