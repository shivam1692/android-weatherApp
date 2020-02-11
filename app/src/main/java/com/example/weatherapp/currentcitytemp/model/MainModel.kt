package com.example.weatherapp.currentcitytemp.model

import com.google.gson.annotations.SerializedName

data class MainModel( @SerializedName("temp") val temp:Float,
                      @SerializedName("temp_min") val tempMin:Float,
                      @SerializedName("temp_max") val tempMax:Float)