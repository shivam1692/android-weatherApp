package com.example.weatherapp.dagger.module

import com.example.weatherapp.base.WeatherRepo
import com.example.weatherapp.currenttemp.repository.FakeRepository
import dagger.Binds
import dagger.Module

@Module
abstract class FakeRepositoryModule {

    @Binds
    abstract fun repository(fakeRepository:FakeRepository):WeatherRepo


}