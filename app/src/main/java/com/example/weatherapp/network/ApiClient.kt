package com.example.weatherapp.network

import android.os.Build
import com.example.weatherapp.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiClient {



        private const val baseURL = "https://api.openweathermap.org/data/"
       private val gson = Gson()
        fun getClient(): Retrofit {
            val httpClient = OkHttpClient.Builder()
            httpClient.connectTimeout(100, TimeUnit.SECONDS)
            httpClient.readTimeout(100, TimeUnit.SECONDS)
            httpClient.writeTimeout(100, TimeUnit.SECONDS)
            httpClient.addInterceptor(RequestInterceptor())

            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                httpClient.addInterceptor(httpLoggingInterceptor)
            }

            return Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build()
        }

}