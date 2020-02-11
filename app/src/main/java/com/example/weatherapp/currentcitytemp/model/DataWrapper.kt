package com.example.weatherapp.currentcitytemp.model

import com.example.weatherapp.network.ErrorResponseModel

class DataWrapper<T> {

    var data:T?=null
    var errorModel:ErrorResponseModel?=null
}