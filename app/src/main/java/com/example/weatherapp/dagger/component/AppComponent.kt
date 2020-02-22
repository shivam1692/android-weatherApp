package com.example.weatherapp.dagger.component

import com.example.weatherapp.base.MyApplication
import com.example.weatherapp.currentcitytemp.view.CurrentCityTempFragment
import com.example.weatherapp.currenttemp.view.CurrentTempFragment
import com.example.weatherapp.dagger.module.RepositoryModule
import com.example.weatherapp.dagger.module.RetrofitModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * App level dagger component.
 *
 */
@Singleton
@Component(modules = [RetrofitModule::class,RepositoryModule::class, AndroidSupportInjectionModule::class, AndroidInjectionModule::class])
interface AppComponent {


    fun getRetrofitModule():Retrofit?


    fun inject(currentCityTempFragment: CurrentCityTempFragment)
    fun inject(application:MyApplication)

    fun inject(currentTempFragment: CurrentTempFragment)
}