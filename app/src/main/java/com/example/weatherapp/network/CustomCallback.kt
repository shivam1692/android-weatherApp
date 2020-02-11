package com.example.weatherapp.network



abstract class CustomCallback<T> {

    abstract fun onSuccessfulResponse(responseBody: T?)
    abstract fun onFailure(errorResponseModel: ErrorResponseModel)
}