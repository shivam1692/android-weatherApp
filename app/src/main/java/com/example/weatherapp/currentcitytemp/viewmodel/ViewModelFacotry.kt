package com.example.weatherapp.currentcitytemp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.base.WeatherRepo
import com.example.weatherapp.currentcitytemp.repository.Repository
import com.example.weatherapp.currenttemp.view.CurrentTempFragment
import com.example.weatherapp.currenttemp.viewmodel.CurrentTempViewModel
import javax.inject.Inject

class ViewModelFactory  constructor(
    private val repository: WeatherRepo,
    val context: Application
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(CurrentCityTempViewModel::class.java) ->
                    CurrentCityTempViewModel(context,repository)
                isAssignableFrom(CurrentTempViewModel::class.java) ->
                    CurrentTempViewModel(context,repository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}