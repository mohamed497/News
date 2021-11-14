package com.example.news.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.R
import com.example.news.pojo.ArticlesModel

class NewsAdapter : PagingDataAdapter<ArticlesModel, MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticlesModel>() {
            override fun areItemsTheSame(oldItem: ArticlesModel, newItem: ArticlesModel): Boolean {
                return oldItem.newsId == newItem.newsId

            }

            override fun areContentsTheSame(
                oldItem: ArticlesModel,
                newItem: ArticlesModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val articlesModel: ArticlesModel? = getItem(position)
        holder.bind(articlesModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(parent)
    }
}


class MyViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.list_news_item, parent, false)
) {
    private val newsTitle = itemView.findViewById<TextView>(R.id.newsTitle)
    private val newsAuthor = itemView.findViewById<TextView>(R.id.newsauthor)
    private val newsImg = itemView.findViewById<ImageView>(R.id.newsImg)

    var articlesModel: ArticlesModel? = null

    fun bind(articlesModel: ArticlesModel?) {
        this.articlesModel = articlesModel
        newsTitle.text = articlesModel?.title
        newsAuthor.text = articlesModel?.author
        Glide.with(newsImg)
            .load(articlesModel?.urlToImage)
            .circleCrop()
            .into(newsImg)
    }
}
