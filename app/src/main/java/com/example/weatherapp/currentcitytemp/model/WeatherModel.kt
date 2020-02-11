package com.example.weatherapp.currentcitytemp.model

import com.google.gson.annotations.SerializedName

data class WeatherModel(@SerializedName("main") val main:String?,
                        @SerializedName("description") val description:String?,
                        @SerializedName("icon") val icon:String?)