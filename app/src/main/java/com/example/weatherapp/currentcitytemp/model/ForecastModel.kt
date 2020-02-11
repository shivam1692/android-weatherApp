package com.example.weatherapp.currentcitytemp.model

data class ForecastModel(val date:String, val city:String, val threeHoursList:ArrayList<ThreeHoursForecastModel>) {


}