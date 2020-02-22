package com.example.weatherapp.currentcitytemp

import android.app.Application
import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weatherapp.R
import com.example.weatherapp.currentcitytemp.viewmodel.CurrentCityTempViewModel
import com.example.weatherapp.currenttemp.repository.FakeRepository
import com.example.weatherapp.getOrAwaitValue
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CurrentCityTempViewModelTest {
    private lateinit var viewModel:CurrentCityTempViewModel
    private lateinit var application: Application






    @Before
    fun setupViewModel(){
        application  = ApplicationProvider.getApplicationContext<Application>()
        viewModel = CurrentCityTempViewModel(application,FakeRepository())
    }



    @Test
    fun getWeatherForecast_testErrorHandlingForEmpyResponse(){
       viewModel.getWeatherForecast("10.23456","10.9346").getOrAwaitValue {
           Assert.assertEquals(application.getString(R.string.no_data_found),viewModel.errorMessage.getOrAwaitValue())
       }

        }



    @Test
    fun getWeatherForecast_testErrorHandlingForApiError(){
            viewModel.getWeatherForecast("10.23457","10.9347").getOrAwaitValue {
                Assert.assertEquals(application.getString(R.string.api_city_not_found),viewModel.errorMessage.value)
            }
        }


    @Test
    fun getWeatherForecast_testValidResponseAndParsing(){
            viewModel.getWeatherForecast("10.234547","10.93474").getOrAwaitValue {
                Assert.assertEquals(1,viewModel.weatherForecastList.value?.size?:0)
                Assert.assertEquals(2,viewModel.weatherForecastList.value?.getOrNull(0)?.threeHoursList?.size?:2)
            }

    }


}