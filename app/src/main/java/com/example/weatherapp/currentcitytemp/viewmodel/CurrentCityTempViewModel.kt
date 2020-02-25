package com.example.weatherapp.currentcitytemp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.R
import com.example.weatherapp.base.WeatherRepo
import com.example.weatherapp.currentcitytemp.model.ForecastModel
import com.example.weatherapp.currentcitytemp.model.TemperatureModel
import com.example.weatherapp.currentcitytemp.model.ThreeHoursForecastModel
import com.example.weatherapp.utils.AppConstants
import com.example.weatherapp.utils.Utils
import java.util.logging.Handler

class CurrentCityTempViewModel(application: Application, private val repository: WeatherRepo) :
    AndroidViewModel(application) {

    /**
    * This livedata is used to updated the UI to show LOADING while consuming API and
    * to show list if data is fetched
    * */
    private val _viewToBeViewed:MediatorLiveData<Int> by lazy {
        MediatorLiveData<Int>()
    }
    val viewToBeViewed:MediatorLiveData<Int>
    get()= _viewToBeViewed


    /**
    *
    * This livedata is used to show error message on the UI with retry button
    * */
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: MutableLiveData<String>
        get() = _errorMessage

    private val _weatherForecastList = MutableLiveData<ArrayList<ForecastModel>>()
    val weatherForecastList: LiveData<ArrayList<ForecastModel>>
        get() = _weatherForecastList




    /**
     *This functions call the api to get the weather Forecast for current city on
     * behalf of latitude and longitude
     *
     * @param lat
     * @param lon
     * @return [LiveData] to provide updates for view
     */
    fun getWeatherForecast(lat: String?, lon: String?):LiveData<Int> {
        _viewToBeViewed.apply {
            postValue(AppConstants.SHOW_LOADING)
            addSource(repository.getWeatherForecastForCurrentCity(lat, lon)) {
                when {
                    it.errorModel != null -> {
                        _errorMessage.postValue(it.errorModel?.message)
                        postValue(AppConstants.SHOW_ERROR)
                    }
                    it.data?.list.isNullOrEmpty() -> {
                        _errorMessage.postValue(getApplication<Application>().getString(R.string.no_data_found))
                        postValue(AppConstants.SHOW_ERROR)

                    }
                    else -> {
                            parseForecastResponseModel(it.data!!.list, it.data?.cityModel?.name ?: "")
                            postValue(AppConstants.SHOW_DATA)

                    }
                }

            }
        }

        return _viewToBeViewed
    }



    /**
     * This method parse the response and put all the data into the list and notifies the observer
     *
     * @param list
     * @param city
     */
    private fun parseForecastResponseModel(list: ArrayList<TemperatureModel>, city: String) {
        val forecastList = ArrayList<ForecastModel>()
        var threeHoursForecastList = ArrayList<ThreeHoursForecastModel>()
        var currentDate = ""

        for((index,value) in list.withIndex()) {
            val tempDate = Utils.parseDateInSpecificFormat(
                value.dateText,
                Utils.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS,
                Utils.DATE_FORMAT_EEEE_DD_MMM_YYYY
            )
            when {

                currentDate.isEmpty() -> {
                    currentDate = tempDate
                    parseThreeHoursForecast(threeHoursForecastList, value)
                }
                currentDate != tempDate -> {
                    forecastList.add(createForeCastModel(currentDate,city,threeHoursForecastList))
                    threeHoursForecastList = ArrayList()
                    currentDate = tempDate
                    parseThreeHoursForecast(threeHoursForecastList, value)
                    if(index==list.size-1){
                        forecastList.add(createForeCastModel(currentDate,city,threeHoursForecastList))
                    }

                }
                else -> {
                    parseThreeHoursForecast(threeHoursForecastList, value)
                    if(index==list.size-1){
                        forecastList.add(createForeCastModel(currentDate,city,threeHoursForecastList))
                    }
                }
            }

        }

        _weatherForecastList.postValue(forecastList)

    }


    private fun createForeCastModel(currentDate:String,city:String,threeHoursForecastList:ArrayList<ThreeHoursForecastModel>):ForecastModel{
        return ForecastModel(getDateOfForecast(currentDate),city,threeHoursForecastList)
    }


    /**
     * Method is to return Today and for Tomorrow instead of date for today and tomorrow and for rest it returns date
     *
     * @param date
     * @return formatted date
     */
    private fun getDateOfForecast(date: String): String {
        return when {
            Utils.isToday(date, Utils.DATE_FORMAT_EEEE_DD_MMM_YYYY) -> {
                getApplication<Application>().getString(R.string.today)
            }
            Utils.isTomorrow(date, Utils.DATE_FORMAT_EEEE_DD_MMM_YYYY) -> {
                getApplication<Application>().getString(R.string.tomorrow)
            }
            else -> {
                date
            }
        }

    }

    /**
    * Parsing the three hours weather forecast and adding it into the list
    * */
    private fun parseThreeHoursForecast(list: ArrayList<ThreeHoursForecastModel>, temperatureModel: TemperatureModel) {
        var weatherDescription:String? = null
        var icon:String? = null
        if (!temperatureModel.weatherList.isNullOrEmpty()) {
            with(temperatureModel.weatherList[0]){
               weatherDescription = description
               icon= this.icon
            }
        }
        ThreeHoursForecastModel(
            Utils.parseDateInSpecificFormat(
                temperatureModel.dateText,
                Utils.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS,
                Utils.DATE_FORMAT_hh_mm_a
            ),
            temperatureModel.windModel?.speed,
            temperatureModel.mainModel?.temp,
            temperatureModel.mainModel?.tempMax,
            temperatureModel.mainModel?.tempMin,
            weatherDescription,
            icon).apply {
            list.add(this)
        }

    }

}
