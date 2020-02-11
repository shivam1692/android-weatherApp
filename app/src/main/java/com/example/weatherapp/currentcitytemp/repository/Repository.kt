package com.example.weatherapp.currentcitytemp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.base.WeatherRepo
import com.example.weatherapp.currentcitytemp.model.*
import com.example.weatherapp.network.*
import retrofit2.Retrofit
import javax.inject.Inject

class Repository @Inject constructor(val retrofit:Retrofit?):WeatherRepo {


   override fun getWeatherForecastForCurrentCity(lat:String?, lon:String?):LiveData<DataWrapper<CurrentCityTempResponseModel>>{
        val liveData = MutableLiveData<DataWrapper<CurrentCityTempResponseModel>>()
        val dataWrapper = DataWrapper<CurrentCityTempResponseModel>()
        retrofit?.create(ApiService::class.java)?.getTempForCurrentCity(lat,lon)?.enqueue(RetrofitCallback(object:
            CustomCallback<CurrentCityTempResponseModel>(){
            override fun onSuccessfulResponse(responseBody: CurrentCityTempResponseModel?) {
                dataWrapper.data=responseBody
                liveData.postValue(dataWrapper)
            }

            override fun onFailure(errorResponseModel: ErrorResponseModel) {
                dataWrapper.errorModel =errorResponseModel
                liveData.postValue(dataWrapper)
            }


        }))

        return liveData
    }


   override fun getCurrentTemperatureForCity(cityName:String):LiveData<DataWrapper<TemperatureModel>>{
        val liveData = MutableLiveData<DataWrapper<TemperatureModel>>()
        val dataWrapper = DataWrapper<TemperatureModel>()
        retrofit?.create(ApiService::class.java)?.getCurrentTempForCity(cityName)?.enqueue(RetrofitCallback(object:
            CustomCallback<TemperatureModel>(){
            override fun onSuccessfulResponse(responseBody: TemperatureModel?) {
                dataWrapper.data=responseBody
                liveData.postValue(dataWrapper)
            }

            override fun onFailure(errorResponseModel: ErrorResponseModel) {
                dataWrapper.errorModel =errorResponseModel
                liveData.postValue(dataWrapper)
            }


        }))

        return liveData
    }







}