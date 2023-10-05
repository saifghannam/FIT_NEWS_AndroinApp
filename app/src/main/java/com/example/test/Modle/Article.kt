package com.example.test.Modle

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Article(

        @SerializedName("author")
        @Expose
        val author: String? = "",

        @SerializedName("description")
        @Expose
        val description: String? = "",


        @SerializedName("title")
        @Expose
        val title: String? = "",

        @SerializedName("content")
        @Expose
        val content: String? = "",


        @SerializedName("publishedAt")
        @Expose
        val publishedAt: String? = "",

        @SerializedName("urlToImage")
        @Expose
        val urlToImage: String? = "",


        var isliked:Boolean=false

    )