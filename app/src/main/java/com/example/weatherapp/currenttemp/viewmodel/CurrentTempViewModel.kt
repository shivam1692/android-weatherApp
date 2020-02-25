package com.example.weatherapp.currenttemp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.weatherapp.R
import com.example.weatherapp.base.WeatherRepo
import com.example.weatherapp.currentcitytemp.model.TemperatureModel
import com.example.weatherapp.currenttemp.model.CurrentTemperatureModel
import com.example.weatherapp.utils.AppConstants
import com.example.weatherapp.utils.Utils
import com.example.weatherapp.utils.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.CountDownLatch

class CurrentTempViewModel(application: Application, private val repository: WeatherRepo) :
    AndroidViewModel(application) {


    private val citiesListSent: ArrayList<String> by lazy {
        ArrayList<String>()
    }

    private val citiesListReceived: ArrayList<String> by lazy {
        ArrayList<String>()
    }

    private val _temperatureList: MutableLiveData<ArrayList<CurrentTemperatureModel>> by lazy {
        MutableLiveData<ArrayList<CurrentTemperatureModel>>()
    }
    val temperatureList: MutableLiveData<ArrayList<CurrentTemperatureModel>>
        get() = _temperatureList


    /**
    * This livedata is used to updated the UI to show LOADING while consuming API and
    * to show list if data is fetched
    * */
    private val _viewToBeShown: MediatorLiveData<Int> by lazy {
        MediatorLiveData<Int>().also {
            it.postValue(AppConstants.SHOW_INFO)
        }
    }
    val viewToBeShown: MediatorLiveData<Int>
        get() = _viewToBeShown





    /**
    * This method is to validate the entered text and update the UI with the error
     *
     * @param cities
     * @return error text
    * */
    fun validate(cities: String): String {
        return if (cities.isEmpty()) {
            getApplication<Application>().getString(R.string.enter_cities_name)
        } else {
            citiesListSent.clear()
            cities.split(",").also { list ->
                list.forEach {
                    with(it.trim()) {
                        if (isNotBlank() && !citiesListSent.contains(this)) {
                            citiesListSent.add(toLowerCase())
                        }
                    }

                }
            }

            when {
                citiesListSent.size < 3 -> {
                    getApplication<Application>().getString(R.string.enter_three_cities)
                }
                citiesListSent.size > 7 -> {
                    getApplication<Application>().getString(R.string.max_seven_cities_allowed)
                }
                else -> {
                    ""
                }
            }


        }
    }

    /**
    * This method is responsible to make api call to get current temperate of entered cities.
     *
     * @return [LiveData] to update the view
    * */
    fun getCurrentTemperature(): LiveData<Int> {
        val temperatureList = ArrayList<CurrentTemperatureModel>()
        val countDownLatch = CountDownLatch(citiesListSent.size)
        citiesListReceived.clear()
        citiesListSent.forEach {
            _viewToBeShown.addSource(repository.getCurrentTemperatureForCity(it)) { dataWrapper ->

                if (dataWrapper.data != null) {
                    parseTemperatureModel(dataWrapper.data, temperatureList)
                } else if(countDownLatch.count==1L && temperatureList.size == 0) {
                    dataWrapper?.errorModel?.message?.showToast(getApplication())
                }
                countDownLatch.countDown()
            }

        }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                countDownLatch.await()
                    _temperatureList.postValue(temperatureList)
                    when {
                        citiesListSent.size == temperatureList.size -> {
                            _viewToBeShown.postValue(AppConstants.SHOW_DATA)
                        }
                        temperatureList.size > 0 -> {
                            _viewToBeShown.postValue(AppConstants.SHOW_DATA)
                            showNotFoundErrorForCities()
                        }
                        else -> {
                            _viewToBeShown.postValue(AppConstants.SHOW_INFO)

                        }
                    }
            }
        }

        return _viewToBeShown
    }


    /**
    *
    * This method is to show city name in the error message if some of the cities data is fetched
    * */
    private fun showNotFoundErrorForCities() {
        val builder = StringBuilder().apply {
            citiesListSent.forEach {
                if (!citiesListReceived.contains(it)) {
                    if (isNotEmpty()) {
                        append(", ")
                    }
                    append(it)
                }
            }
        }
        builder.append(getApplication<Application>().getString(R.string.city_not_found))
        builder.toString().showToast(getApplication())
    }


    private fun parseTemperatureModel(temperatureModel: TemperatureModel?, temperatureList: ArrayList<CurrentTemperatureModel>) {
        temperatureModel?.let {
            citiesListReceived.add(it.cityName.toLowerCase())
            val weatherModel = it.weatherList?.getOrNull(0)
            temperatureList.add(
                CurrentTemperatureModel(
                    it.windModel?.speed,
                    it.mainModel?.temp,
                    it.mainModel?.tempMax,
                    it.mainModel?.tempMin,
                    weatherModel?.description,
                    weatherModel?.icon,
                    Utils.parseUnixTimeStampInSpecificFormat(it.date,Utils.DATE_FORMAT_EEEE_DD_MMM_YYYY),
                    it.cityName
                )
            )
        }
    }

    fun showLoading(){
        _viewToBeShown.postValue(AppConstants.SHOW_LOADING)
    }


}
