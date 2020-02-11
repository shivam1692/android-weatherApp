package com.example.weatherapp

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.example.weatherapp.base.MyApplicationTest

class TestRunner: AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, MyApplicationTest::class.java.name, context)
    }
}