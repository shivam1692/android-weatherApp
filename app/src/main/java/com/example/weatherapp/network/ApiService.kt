package com.example.weatherapp.network

import com.example.weatherapp.currentcitytemp.model.CurrentCityTempResponseModel
import com.example.weatherapp.currentcitytemp.model.TemperatureModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("2.5/forecast")
    fun getTempForCurrentCity(@Query("lat") lat:String?, @Query("lon") lon:String?):Call<CurrentCityTempResponseModel>

    @GET("2.5/weather")
    fun getCurrentTempForCity(@Query("q") cityName:String?):Call<TemperatureModel>
}


