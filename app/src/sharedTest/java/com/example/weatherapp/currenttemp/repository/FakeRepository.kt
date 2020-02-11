package com.example.weatherapp.currenttemp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.base.WeatherRepo
import com.example.weatherapp.currentcitytemp.model.*
import com.example.weatherapp.network.ErrorResponseModel
import javax.inject.Inject

class FakeRepository @Inject constructor() : WeatherRepo {
    private val cityList = listOf("mumbai", "delhi", "bangkok", "gurgaon", "new york", "khatauli","noida")

    override fun getWeatherForecastForCurrentCity(lat: String?, lon: String?): LiveData<DataWrapper<CurrentCityTempResponseModel>> {
        val liveData = MutableLiveData<DataWrapper<CurrentCityTempResponseModel>>()
        val dataWrapper = DataWrapper<CurrentCityTempResponseModel>()
        if(lat?.equals("10.23456") == true && lon?.equals("10.9346")==true){
            dataWrapper.data = CurrentCityTempResponseModel("200",ArrayList(), CityModel(""))
        }else if(lat?.equals("10.23457") == true && lon?.equals("10.9347")==true){

            dataWrapper.errorModel = ErrorResponseModel("404","City not found")
            liveData.postValue(dataWrapper)


        }else{
            val list = ArrayList<TemperatureModel>()
            TemperatureModel(1578409200,
                MainModel(284.92f,283.58f,284.92f),
                ArrayList<WeatherModel>().apply {
                    add(WeatherModel("Clouds","overcast clouds","04d"))
                },
                WindModel(5.19f),
                "2020-01-07 15:00:00","").apply {
                list.add(this)
                list.add(this)
            }

            dataWrapper.data = CurrentCityTempResponseModel("200",list, CityModel("Delhi"))
            liveData.postValue(dataWrapper)

        }


        return liveData

    }

    override fun getCurrentTemperatureForCity(cityName: String): LiveData<DataWrapper<TemperatureModel>> {
        val liveData = MutableLiveData<DataWrapper<TemperatureModel>>()
        val dataWrapper = DataWrapper<TemperatureModel>()
        if (cityList.contains(cityName.toLowerCase())) {
            val list = ArrayList<WeatherModel>().apply {
                add(WeatherModel("Clear","Clear sky","100n"))
            }
            liveData.postValue(dataWrapper.apply {
                data = TemperatureModel(1033304444,
                    MainModel(20.33f,18.33f,24.55f),
                    list,
                    WindModel(10.03f),
                    "2020-10-10 12:00:04",cityName)
            })
        } else {
            liveData.postValue(dataWrapper.apply {
                errorModel = ErrorResponseModel("404", "City not found")
            })
        }


        return liveData
    }


}