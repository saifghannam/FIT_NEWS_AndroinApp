package com.example.test.Modle

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Headlines(
    @SerializedName("articles")
    @Expose
    val articles: List<Article>? = null,
    @SerializedName("status")
    @Expose
    val status: String? = null,
    @SerializedName("totalResults")
    @Expose
    val totalResults: Int = 0
)