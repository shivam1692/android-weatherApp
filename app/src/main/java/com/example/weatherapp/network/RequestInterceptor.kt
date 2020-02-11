package com.example.weatherapp.network

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request();
        val originalHttpUrl = request.url
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("APPID","5a943dda8f7e16226394b6631a4efbef")
            .addQueryParameter("units","metric")
            .build()

        val requestBuilder = request.newBuilder().url(url)
        val newRequest = requestBuilder.build()
        return  chain.proceed(newRequest)

    }
}