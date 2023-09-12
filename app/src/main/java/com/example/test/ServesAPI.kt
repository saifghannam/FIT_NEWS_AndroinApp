package com.example.test


import com.example.test.Modle.Headlines
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ServesAPI {

    @GET("top-headlines")

    fun getPost(@Query("country") country:String, @Query("apiKey") apiKey:String): Call<Headlines>

}