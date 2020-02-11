package com.example.weatherapp.dagger.component


import com.example.weatherapp.base.MyApplication
import com.example.weatherapp.base.MyApplicationTest
import com.example.weatherapp.dagger.module.FakeRepositoryModule
import com.example.weatherapp.dagger.module.RetrofitModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class,FakeRepositoryModule::class, AndroidSupportInjectionModule::class, AndroidInjectionModule::class])
interface TestAppComponent:AppComponent