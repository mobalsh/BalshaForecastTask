package com.balsha.forecasttask.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.balsha.forecasttask.data.database.converters.CitiesConverter
import com.balsha.forecasttask.data.database.converters.CitiesCoordinatesConverter
import com.balsha.forecasttask.data.database.converters.ForecastsCloudsConverter
import com.balsha.forecasttask.data.database.converters.ForecastsConverter
import com.balsha.forecasttask.data.database.converters.ForecastsMainConverter
import com.balsha.forecasttask.data.database.converters.ForecastsSysConverter
import com.balsha.forecasttask.data.database.converters.ForecastsWeatherConverter
import com.balsha.forecasttask.data.database.converters.ForecastsWindConverter
import com.balsha.forecasttask.data.model.city.CityModel
import com.balsha.forecasttask.data.model.forecast.ForecastModel

@Database(entities = [CityModel::class, ForecastModel::class], version = 1, exportSchema = false)
@TypeConverters(
    CitiesConverter::class,
    CitiesCoordinatesConverter::class,
    ForecastsConverter::class,
    ForecastsCloudsConverter::class,
    ForecastsMainConverter::class,
    ForecastsSysConverter::class,
    ForecastsWeatherConverter::class,
    ForecastsWindConverter::class
)
abstract class ForecastDatabase : RoomDatabase() {
    abstract fun forecastDao(): ForecastDao
}