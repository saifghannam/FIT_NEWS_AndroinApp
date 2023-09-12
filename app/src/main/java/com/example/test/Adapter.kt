package com.example.test

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test.Modle.Article

class Adapter(context: Context, private val article: List<Article>) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    private val context: Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentArticle = article[position]

        holder.titleNews.text = currentArticle.title
        holder.descriptionNews.text = currentArticle.description
        holder.authorNews.text = currentArticle.author

        Glide.with(holder.itemView.context)
            .load(currentArticle.urlToImage)
            .error(R.drawable.ic_launcher_background)
            .into(holder.imgNews)
    }

    override fun getItemCount(): Int {
        return article.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleNews: TextView = itemView.findViewById(R.id.Title_News_ID)
        val descriptionNews: TextView = itemView.findViewById(R.id.Descrpition_News_ID)
        val authorNews: TextView = itemView.findViewById(R.id.Auter_News_ID)
        val imgNews: ImageView = itemView.findViewById(R.id.Img_NEWS_ID)
    }
}
