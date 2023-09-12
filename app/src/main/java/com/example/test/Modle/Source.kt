package com.example.test.Modle

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("id")
    @Expose
    val id: String? = null,
    @SerializedName("name")
    @Expose
    val name: String? = null
)