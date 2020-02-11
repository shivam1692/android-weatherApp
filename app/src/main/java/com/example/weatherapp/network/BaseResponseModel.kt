package com.example.weatherapp.network

import com.google.gson.annotations.SerializedName



class BaseResponseModel {



    @SerializedName("succ")
    var isSuccess =false

    @SerializedName("public_msg")
    var message =""

    @SerializedName("_err_codes")
    var errorCode=ArrayList<String>()


    @SerializedName("data")
    var responseModel = null


}

