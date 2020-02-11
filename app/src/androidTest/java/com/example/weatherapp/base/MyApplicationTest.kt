package com.example.weatherapp.base

import com.example.weatherapp.dagger.component.AppComponent
import com.example.weatherapp.dagger.component.DaggerTestAppComponent
import com.example.weatherapp.dagger.component.TestAppComponent


class MyApplicationTest: MyApplication(){

    override fun initAppComponent(): AppComponent {
        return DaggerTestAppComponent.builder().build()
    }
}