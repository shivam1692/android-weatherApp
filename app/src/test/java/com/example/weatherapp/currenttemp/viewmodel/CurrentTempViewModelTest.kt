package com.example.weatherapp.currenttemp.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weatherapp.R
import com.example.weatherapp.currenttemp.repository.FakeRepository
import com.example.weatherapp.getOrAwaitValue
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CurrentTempViewModelTest {

    private lateinit var viewModel: CurrentTempViewModel
    private lateinit var application: Application


    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        application = ApplicationProvider.getApplicationContext<Application>()
        viewModel = CurrentTempViewModel(application, FakeRepository())
    }

    @Test
    fun validateAndCallApi_emptyCitiesValidation() {
        val validation = viewModel.validate("")
        assertEquals(application.getString(R.string.enter_cities_name), validation)
    }

    @Test
    fun validateAndCallApi_lessThanThreeValidation() {
        val validation = viewModel.validate("Mumbai,Delhi")
        assertEquals(application.getString(R.string.enter_three_cities), validation)
    }

    @Test
    fun validateAndCallApi_moreThanSevenValidation() {
        val validation = viewModel.validate("Mumbai,Delhi,Abc,Def,Jhi,Jkl,ASD,DEF")
        assertEquals(application.getString(R.string.max_seven_cities_allowed), validation)
    }


    @Test
    fun validateAndCallApi_citiesListValidations() {
        viewModel.validate("Mumbai,Delhi,Bangkok").also {
            viewModel.getCurrentTemperature().getOrAwaitValue()
            assertEquals(3, viewModel.temperatureList.value?.size ?: 0)
//                    }


        }

    }


    @Test
    fun validateAndCallApi_citiesListMaxListValidations() {
        viewModel.validate("Mumbai,Delhi,Bangkok,Gurgaon,New York,Khatauli,Noida").also {

            viewModel.getCurrentTemperature().getOrAwaitValue()
            assertEquals(7, viewModel.temperatureList.value?.size ?: 0)


        }
    }

    @Test
    fun validateAndCallApi_inValidCitiesValidation() {
        viewModel.validate("abcd,efgh,hij,klm").also {

            viewModel.getCurrentTemperature().getOrAwaitValue()
            assertEquals(0, viewModel.temperatureList.value?.size ?: 0)


        }

    }


}

