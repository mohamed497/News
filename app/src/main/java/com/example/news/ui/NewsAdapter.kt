package com.example.news.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.R
import com.example.news.pojo.ArticlesModel

class NewsAdapter(private val newsList: List<ArticlesModel>) :
    ListAdapter<ArticlesModel, NewsViewHolder>(
        UserDiffCallBack()
    ) {


    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val articlesModel: ArticlesModel? = newsList[position]
        holder.bind(articlesModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}

class NewsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
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

class UserDiffCallBack : DiffUtil.ItemCallback<ArticlesModel>() {

    override fun areItemsTheSame(oldItem: ArticlesModel, newItem: ArticlesModel): Boolean {
        return oldItem.publishedAt == newItem.publishedAt
    }

    override fun areContentsTheSame(oldItem: ArticlesModel, newItem: ArticlesModel): Boolean {
        return oldItem == newItem
    }

}
