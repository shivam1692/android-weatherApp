package com.example.weatherapp.currentcitytemp.model

import com.google.gson.annotations.SerializedName

data class TemperatureModel(@SerializedName("dt") val date:Int,
                            @SerializedName("main") val mainModel:MainModel?,
                            @SerializedName("weather") val weatherList:ArrayList<WeatherModel>?,
                            @SerializedName("wind") val windModel:WindModel?,
                            @SerializedName("dt_txt") val dateText:String?,
                            @SerializedName("name") val cityName:String)