package com.example.weatherapp.landing

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.GrantPermissionRule
import com.example.weatherapp.R
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

class HomeFragmentTest {





    @Test
    fun currentCityButton_visible(){
        val scenario = launchFragmentInContainer<HomeFragment>(themeResId = R.style.AppTheme)
//

        onView(withId(R.id.btnCurrentCity)).check(matches(isDisplayed()))
    }

    @Test
    fun currentTempButton_visible(){
        val scenario = launchFragmentInContainer<HomeFragment>(themeResId = R.style.AppTheme)
//        val navController = mock(NavController::class.java)
//        scenario.onFragment {
//            Navigation.setViewNavController(it.view!!,navController)
//
//        }

        onView(withId(R.id.btnSearchCities)).check(matches(isDisplayed()))
    }

    @Test
    fun clickCurrentCityButton_navigateToCurrentCityFragment(){
        val scenario = launchFragmentInContainer<HomeFragment>(themeResId = R.style.AppTheme)
        val navController = mock(NavController::class.java)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!,navController)

        }

        onView(withId(R.id.btnCurrentCity)).perform(click())
        verify(navController).navigate(R.id.action_homeFragment_to_currentCityTempFragment)
    }




    @Test
    fun clickSearchCitiesButton_navigateToCurrentTempFragment(){
        val scenario = launchFragmentInContainer<HomeFragment>(themeResId =  R.style.AppTheme)
        val navController = mock(NavController::class.java)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!,navController)

        }

        onView(withId(R.id.btnSearchCities)).perform(click())

        verify(navController).navigate(R.id.action_homeFragment_to_currentTempFragment)
    }
}