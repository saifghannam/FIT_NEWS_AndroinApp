package com.example.test

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test.Modle.Article
import com.example.test.Modle.GeneralData

class Adapter2(context: Context, private var articleList: ArrayList<Article>) :
    RecyclerView.Adapter<Adapter2.ViewHolder>() {

    // Define the interface for item click
    interface OnListItemClick {
        fun onItemClick(article: Article)
    }

    var onListItemClick: OnListItemClick? = null

    private val context: Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentArticle = articleList[position]
        // Set the article title and image
        holder.titleNews.text = currentArticle.title
        Glide.with(holder.itemView.context)
            .load(currentArticle.urlToImage)
            .error(R.drawable.placeholder)
            .into(holder.imgNews)


        // Handle item click
        holder.itemView.setOnClickListener {
            // Notify the listener when an item is clicked
            onListItemClick?.onItemClick(currentArticle)

            // Create and show the ClickedNewsFragment
            val fragment = ClickedNewsFragment()

            // Pass data to the fragment using Bundle
            val bundle = Bundle()
            bundle.putString("titleNews", currentArticle.title)
            bundle.putString("description", currentArticle.description)
            bundle.putString("urlToImage", currentArticle.urlToImage)
            bundle.putString("publishedAt", currentArticle.publishedAt)
            bundle.putString("content", currentArticle.content)
            fragment.arguments = bundle

            // Replace the current fragment with ClickedNewsFragment
            val activity = context as MainActivity
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.home_fragment_id, fragment)
                .commit()
        }
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleNews: TextView = itemView.findViewById(R.id.Title_News_ID)
        val imgNews: ImageView = itemView.findViewById(R.id.Img_NEWS_ID)
     //   val checkBox: CheckBox = itemView.findViewById(R.id.checkbox_meat)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFilterList(filteredList: ArrayList<Article>) {
        this.articleList = filteredList
        notifyDataSetChanged()
    }
}
