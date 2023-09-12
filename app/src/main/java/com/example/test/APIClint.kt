package com.example.test

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient private constructor() {

    private val BASE_URL = "https://newsapi.org/v2/"
    private var apiClient: APIClient? = null
    private var retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Synchronized
    fun getInstance(): APIClient {
        if (apiClient == null) {
            apiClient = APIClient()
        }
        return apiClient!!
    }

    fun getApi(): ServesAPI {
        return retrofit.create(ServesAPI::class.java)
    }

}