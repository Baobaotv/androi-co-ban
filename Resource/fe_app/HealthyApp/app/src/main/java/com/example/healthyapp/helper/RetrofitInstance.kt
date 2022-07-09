package com.example.healthyapp.helper

import com.example.healthyapp.data.AppApi
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance private constructor() {
    companion object{
        val client = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also { it.level = HttpLoggingInterceptor.Level.BODY }).build()
        @Volatile
        private var instance:RetrofitInstance?=null
        fun getInstance() = instance?: synchronized(this){
            instance?: synchronized(this){
                instance = RetrofitInstance()
                instance
            }
        }
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://booking-care-app.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create(Gson().newBuilder().setLenient().create()))
        .client(client)
        .build()

    val api:AppApi by lazy { retrofit.create(AppApi::class.java) }
}