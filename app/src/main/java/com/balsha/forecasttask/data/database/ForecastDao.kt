package com.balsha.forecasttask.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.balsha.forecasttask.data.model.city.CityModel
import com.balsha.forecasttask.data.model.forecast.ForecastModel

@Dao
interface ForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCities(cities: ArrayList<CityModel>)

    @Query("SELECT * FROM city_table")
    suspend fun retrieveCities(): List<CityModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecasts(forecast: ArrayList<ForecastModel>)

    @Query("SELECT * FROM forecast_table")
    suspend fun retrieveForecasts(): List<ForecastModel>
}
