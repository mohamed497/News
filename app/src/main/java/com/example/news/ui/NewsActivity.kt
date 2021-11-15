package com.example.news.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.R
import com.example.news.database.NewsDatabase
import com.example.news.database.NewsDatabaseDao
import com.example.news.netowrk.NewsApi
import com.example.news.repository.NewsRepo
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class NewsActivity : AppCompatActivity() {

    private lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)


        initViewModel()
        initRecyclerView()
//
//        viewModel.getNews()
//        viewModel.news.observe(this, Observer { news ->
//            adapter.setMovieList(news)
//
////            Log.d("zxc_get", news[1].author)
//
//        })

//        viewModel.getNewsFromDB()
//        viewModel.loadAllNewsByPage(2,1)
//        viewModel.newsDB.observe(this, Observer { news ->
//            newsAdapter.setMovieList(news)
//            Log.d("NewsActivity", news[1].newsId.toString())
//        })

        lifecycleScope.launch {
            viewModel.items.collectLatest {
                newsAdapter.submitData(it)
            }
        }


    }

    private fun initRecyclerView() {

        newsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@NewsActivity)
            newsAdapter = NewsAdapter()
            adapter = newsAdapter
        }

    }

    private fun initViewModel() {
        val dao: NewsDatabaseDao = NewsDatabase.getInstance(application).newsDatabaseDao
        viewModel =
            ViewModelProvider(this, NewsViewModelFactory(NewsRepo(NewsApi.retrofitService, dao)))
                .get(NewsViewModel::class.java)
    }


}