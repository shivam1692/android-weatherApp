package com.example.weatherapp.network

import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * Custom callback for Retrofit response
 *
 * @property callback
 */
class RetrofitCallback<T>(val callback: CustomCallback<T>) : Callback<T> {


    override fun onResponse(call: Call<T>, response: Response<T>?) {
        if (response == null) {
            callback.onFailure(getBasicErrorModel("999"))
            return
        }
        when (response.code()) {

            200 -> processSuccessResponse(response.body())

            401 -> callback.onFailure(getBasicErrorModel(response.code().toString()))

            404 -> processErrorResponse(response.errorBody()?.string())
            else -> {
                callback.onFailure(getBasicErrorModel(response.code().toString()))
            }
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        if (t is IOException) {
            val message = if (t is SocketTimeoutException) {
                "Server not responding.\nPlease try again."
            } else {
                "Unable to connect to our servers, please check your Internet Connection."
            }
            callback.onFailure(ErrorResponseModel("999",message))
        } else {
            callback.onFailure(getBasicErrorModel("999"))
        }
    }


    private fun processErrorResponse(responseBody:String?){
        val gson = Gson()
        callback.onFailure(gson.fromJson(responseBody,ErrorResponseModel::class.java))
    }


    private fun processSuccessResponse(body: T?) {
            callback.onSuccessfulResponse(body)
    }

    private fun getBasicErrorModel(errorCode:String): ErrorResponseModel {
        return ErrorResponseModel(errorCode,"Something went wrong")
    }
}