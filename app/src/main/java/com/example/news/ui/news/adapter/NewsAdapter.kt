package com.example.news.ui.news.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.R
import com.example.news.pojo.Article
import kotlinx.android.synthetic.main.list_news_item.view.*

class NewsAdapter :
    ListAdapter<Article, NewsViewHolder>(
        UserDiffCallBack()
    ) {

    private val news = ArrayList<Article>()

    fun setNews(news: List<Article>) {
        this.news.addAll(news)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val articlesModel: Article? = news[position]
        holder.bind(articlesModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return news.size
    }
}

class NewsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.list_news_item, parent, false)
) {

    fun bind(articlesModel: Article?) {
        itemView.apply {
            newsTitle.text = articlesModel?.title
            newsauthor.text = articlesModel?.author
            Glide.with(newsImg)
                .load(articlesModel?.urlToImage)
                .circleCrop()
                .into(newsImg)
        }

    }
}

class UserDiffCallBack : DiffUtil.ItemCallback<Article>() {

    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.publishedAt == newItem.publishedAt
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

}
