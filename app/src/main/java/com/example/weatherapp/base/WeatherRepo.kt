package com.example.weatherapp.base

import androidx.lifecycle.LiveData
import com.example.weatherapp.currentcitytemp.model.CurrentCityTempResponseModel
import com.example.weatherapp.currentcitytemp.model.DataWrapper
import com.example.weatherapp.currentcitytemp.model.TemperatureModel

interface WeatherRepo {


    fun getWeatherForecastForCurrentCity(lat:String?, lon:String?):LiveData<DataWrapper<CurrentCityTempResponseModel>>

    fun getCurrentTemperatureForCity(cityName:String):LiveData<DataWrapper<TemperatureModel>>
}