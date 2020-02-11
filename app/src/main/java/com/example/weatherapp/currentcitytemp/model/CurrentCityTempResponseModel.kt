package com.example.weatherapp.currentcitytemp.model

import com.google.gson.annotations.SerializedName

data class CurrentCityTempResponseModel(@SerializedName("cod") val statusCode:String?,
                                        @SerializedName("list") val list:ArrayList<TemperatureModel>,
                                        @SerializedName("city") val cityModel: CityModel?
)