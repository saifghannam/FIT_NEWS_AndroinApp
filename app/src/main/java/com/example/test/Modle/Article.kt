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


        @SerializedName("urlToImage")
        @Expose
        val urlToImage: String? = ""
    )
