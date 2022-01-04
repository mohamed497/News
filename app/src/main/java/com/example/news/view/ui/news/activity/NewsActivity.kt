package com.example.news.view.ui.news.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.news.R
import com.example.news.presentation.base.Resource
import com.example.news.domain.models.Article
import com.example.news.presentation.viewmodels.NewsViewModel
import com.example.news.view.ui.news.adapter.NewsAdapter
import com.example.news.view.utils.debug
import com.example.news.view.utils.error
import com.example.news.view.utils.toGone
import com.example.news.view.utils.toVisible
import kotlinx.android.synthetic.main.activity_news.*
import org.koin.android.viewmodel.ext.android.viewModel

const val SAVE_INSTANCE_NEWS = "News"

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
            debug(getString(R.string.save_instance))
            observeOnNews()
        }
    }

    private fun getSavedInstanceState(savedInstanceState: Bundle?) {
        articleSaveInstance =
            savedInstanceState?.getParcelable(SAVE_INSTANCE_NEWS)!!
        newsAdapter.setNews(articleSaveInstance ?: emptyList())
        debug(getString(R.string.check_instance))
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
                    progress.toGone()
                    errorText.toVisible()
                    error(newsResource.t.toString(), newsResource.t)
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