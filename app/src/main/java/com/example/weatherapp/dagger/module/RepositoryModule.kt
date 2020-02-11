package com.example.weatherapp.dagger.module

import com.example.weatherapp.base.WeatherRepo
import com.example.weatherapp.currentcitytemp.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RepositoryModule {


    @Binds
    abstract fun respository(weatherRepo: Repository):WeatherRepo

}