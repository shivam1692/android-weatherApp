package com.example.weatherapp.currentcitytemp.model

import com.google.gson.annotations.SerializedName

data class CityModel(@SerializedName("name") val name:String) {
}