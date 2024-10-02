package com.balsha.forecasttask.presentation

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.balsha.forecasttask.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testCitySelection() {
        // Click on the dropdown to select a city
        onView(withId(R.id.tvMainCitySelect)).perform(click())

        // Assuming "Cairo" is one of the city options
        onView(withText("Cairo")).perform(click())

        // Verify that the correct forecast is displayed
        onView(withId(R.id.tvMainCitySelect)).check(matches(withText("Cairo")))
    }
}
