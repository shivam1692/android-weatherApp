package com.example.weatherapp.base

import android.app.Activity
import android.app.Application
import android.content.Context
import com.example.weatherapp.dagger.component.AppComponent
import com.example.weatherapp.dagger.component.DaggerAppComponent
import com.example.weatherapp.dagger.module.RetrofitModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

open class MyApplication: Application() {


    private lateinit var _appComponent:AppComponent

    /*
    * Created getter for app component
    * */
    val appComponent:AppComponent
    get() = _appComponent


    override fun onCreate() {
        super.onCreate()
        initAppComponent()
        _appComponent = initAppComponent()
        _appComponent.inject(this)

    }


    /*
    *
    * Initialized dagger app component
    * */
     open fun initAppComponent():AppComponent{
       return DaggerAppComponent.builder()
           .retrofitModule(RetrofitModule())
           .build()


    }





    companion object {
        fun get(context: Context?):MyApplication{
           return context?.applicationContext as MyApplication
        }
    }


}