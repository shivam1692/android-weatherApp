package com.example.weatherapp.currenttemp.view

import android.content.Context
import android.view.KeyEvent
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.internal.inject.InstrumentationContext
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.InstrumentationConnection
import com.example.weatherapp.CustomRecyclerViewAssertion.Companion.hasItemCount
import androidx.test.platform.app.InstrumentationRegistry
import com.example.weatherapp.R
import com.example.weatherapp.landing.MainActivity
import kotlinx.android.synthetic.main.fragment_home.*
import org.junit.Assert.*
import org.junit.Test

class CurrentTempFragmentTest{

    private val context :Context by lazy {
        InstrumentationRegistry.getInstrumentation().targetContext
    }


    @Test
    fun checkBackHandling_FromCurrentTempToHome(){
        val screnario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.btnSearchCities)).perform(click())
        onView(withId(R.id.edtCity)).check(matches(isDisplayed()))
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
        onView(withId(R.id.btnSearchCities)).check(matches(isDisplayed()))
    }



    @Test
    fun checkCityValidation_MinimumCitiesValidation(){
        val scenario = launchFragmentInContainer<CurrentTempFragment>(themeResId = R.style.AppTheme)
        onView(withId(R.id.edtCity)).perform(
            clearText(),
            typeText("Khatauli")
        )
        onView(withId(R.id.ivSearchIcon)).perform(click())
        onView(withText(context.getString(R.string.enter_three_cities))).check(matches(isDisplayed()))
    }

    @Test
    fun checkCityValidation_MaximumCitiesValidation(){
        val scenario = launchFragmentInContainer<CurrentTempFragment>(themeResId = R.style.AppTheme)
        onView(withId(R.id.edtCity)).perform(
            clearText(),
            typeText("Khatauli,Ambala,Chandigarh,Leh,Karnal,Panipat,London,Delhi")
        )
        onView(withId(R.id.ivSearchIcon)).perform(click())
        onView(withText(context.getString(R.string.max_seven_cities_allowed))).check(matches(isDisplayed()))
    }

    @Test
    fun checkDataValidations_CitiesListSize(){
        val scenario = launchFragmentInContainer<CurrentTempFragment>(themeResId = R.style.AppTheme)
        onView(withId(R.id.edtCity)).perform(
            clearText(),
            typeText("Khatauli,Mumbai,Delhi")
        )
        onView(withId(R.id.ivSearchIcon)).perform(click())
        onView(withId(R.id.rvCurrentTemp)).check(hasItemCount(3))
    }

    @Test
    fun checkCityValidation_EmptyCitiesValidation(){
        val scenario = launchFragmentInContainer<CurrentTempFragment>(themeResId = R.style.AppTheme)
        onView(withId(R.id.edtCity)).perform(
            clearText(),
            pressImeActionButton()
        )
        onView(withText(context.getString(R.string.enter_cities_name))).check(matches(isDisplayed()))
    }






//

}