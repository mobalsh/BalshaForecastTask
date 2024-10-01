package com.balsha.forecasttask.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.balsha.forecasttask.data.model.forecast.ForecastModel

@Dao
interface ForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(forecast: ArrayList<ForecastModel>)

    @Query("SELECT * FROM forecast_table")
    suspend fun retrieve(): List<ForecastModel>
}
