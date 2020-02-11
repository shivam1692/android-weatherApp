package com.example.weatherapp.dagger.module

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.network.RequestInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class RetrofitModule() {

   val url = "https://api.openweathermap.org/data/"

    @Provides
    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit? {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun getOkHttpCleint(httpLoggingInterceptor: HttpLoggingInterceptor?): OkHttpClient {
        return OkHttpClient.Builder().run {
            addInterceptor(RequestInterceptor())
            if(BuildConfig.DEBUG) {
                addInterceptor(httpLoggingInterceptor!!)
            }
            build()
        }

    }

    @Provides
    fun getHttpLoggingInterceptor(): HttpLoggingInterceptor? {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

}