package com.balsha.forecasttask

import androidx.room.Room
import com.balsha.forecasttask.app.AppController
import com.balsha.forecasttask.data.database.ForecastDao
import com.balsha.forecasttask.data.database.ForecastDatabase
import com.balsha.forecasttask.data.model.forecast.Clouds
import com.balsha.forecasttask.data.model.forecast.ForecastModel
import com.balsha.forecasttask.data.model.forecast.Main
import com.balsha.forecasttask.data.model.forecast.Sys
import com.balsha.forecasttask.data.model.forecast.Weather
import com.balsha.forecasttask.data.model.forecast.Wind
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ForecastDaoTest {
    private lateinit var database: ForecastDatabase
    private lateinit var forecastDao: ForecastDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            AppController.getApplicationContext(),
            ForecastDatabase::class.java
        ).build()
        forecastDao = database.forecastDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndGetForecast() = runBlocking {
        val forecasts = ArrayList<ForecastModel>()
        forecasts.add(
            ForecastModel(
                id = 1,
                date = 1f,
                main = Main(1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f),
                weather = arrayListOf(Weather(1, "", "", "")),
                clouds = Clouds(1f),
                wind = Wind(1f, 1f, 1f),
                visibility = 1f,
                pop = 1f,
                sys = Sys(""),
                dateText = "",
                cityId = 1
            )
        )
        forecastDao.insertForecasts(forecasts)

        val retrievedForecasts = forecastDao.retrieveForecasts()
        assertEquals(1, retrievedForecasts.size)
        assertEquals(forecasts.first().cityId, retrievedForecasts.first().cityId)
    }
}
