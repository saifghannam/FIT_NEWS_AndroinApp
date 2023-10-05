package com.example.test


import com.example.test.Modle.Headlines
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ServesAPI {
   // @GET("post/1")
   //  fun getPost(): Call<Headlines>



    @GET("top-headlines") //? stop
    fun getPost(@Query("country") country:String, @Query("apiKey") apiKey:String): Call<Headlines>


}