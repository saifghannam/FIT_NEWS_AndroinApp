package com.example.test

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.test.Modle.Article
import com.example.test.Modle.Headlines
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter
    private val article = ArrayList<Article>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerview_id)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = Adapter(this, article)
        recyclerView.adapter = adapter

        // Call the API to retrieve data
        retrieveJson("us")
    }

    private fun retrieveJson(country: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val serviceAPI = retrofit.create(ServesAPI::class.java)

        val apiKey = "517c6e4f02a24c6f821dd5dbae4cc53e" // Replace with your actual API key

        val call: Call<Headlines> = serviceAPI.getPost(country, apiKey)
        call.enqueue(object : Callback<Headlines> {
            override fun onResponse(call: Call<Headlines>, response: Response<Headlines>) {
                if (response.isSuccessful) {
                    // Clear existing data
                    article.clear()

                    // Add new data
                    val headlines = response.body()
                    headlines?.articles?.let { article.addAll(it) }

                    // Notify the adapter of the data change
                    adapter.notifyDataSetChanged()
                } else {
                    Log.d("TAG", "onResponse: Request failed")
                }
            }

            override fun onFailure(call: Call<Headlines>, t: Throwable) {
                Log.d("TAG", "onFailure: ${t.message}")
            }
        })
    }
}
