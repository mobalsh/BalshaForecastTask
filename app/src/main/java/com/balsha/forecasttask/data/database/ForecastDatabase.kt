package com.balsha.forecasttask.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.balsha.forecasttask.data.model.forecast.ForecastModel

@Database(entities = [ForecastModel::class], version = 1, exportSchema = false)
@TypeConverters(
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
/*
abstract class ForecastDatabase : RoomDatabase() {
    abstract fun forecastDao(): ForecastDao

    companion object {
        @Volatile
        private var INSTANCE: ForecastDatabase? = null

        fun getDatabase(context: Context): ForecastDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    AppController.getApplicationContext(),
                    ForecastDatabase::class.java,
                    "forecast_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
*/
