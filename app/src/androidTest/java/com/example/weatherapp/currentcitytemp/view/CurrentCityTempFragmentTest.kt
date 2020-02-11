package com.example.weatherapp.currentcitytemp.view

import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.example.weatherapp.CustomRecyclerViewAssertion
import com.example.weatherapp.CustomRecyclerViewAssertion.Companion.hasItemCount
import com.example.weatherapp.R
import com.example.weatherapp.landing.MainActivity
import org.hamcrest.CoreMatchers.allOf
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith


@MediumTest
@RunWith(AndroidJUnit4::class)
class CurrentCityTempFragmentTest {


    @get:Rule
    var permissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION)


    private val context : Context by lazy {
        InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun checkBackHandling_FromCurrentCityToHome(){
        val screnario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.btnCurrentCity)).perform(click())
        onView(withId(R.id.clCurrentCity)).check(matches(isDisplayed()))
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
        onView(withId(R.id.btnSearchCities)).check(matches(isDisplayed()))
    }

    @Test
    fun forecastRecyclerView_isVisible(){
        val scenario = launchFragmentInContainer<CurrentCityTempFragment>(themeResId = R.style.AppTheme)
        onView(withId(R.id.rvDaysForecast))
            .check(hasItemCount(1))
    }


    @Test
    fun hoursRecyclerView_isVisible(){
        val scenario = launchFragmentInContainer<CurrentCityTempFragment>(themeResId = R.style.AppTheme)
       onView(allOf(isDisplayed(), withId(R.id.rvHoursForecast)))
    }





}