package com.balsha.forecasttask.data.database

import androidx.room.TypeConverter
import com.balsha.forecasttask.data.model.forecast.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ForecastsWeatherConverter {
    @TypeConverter
    fun fromString(value: String): ArrayList<Weather> {
        val listType = object : TypeToken<ArrayList<Weather>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<Weather>): String {
        return Gson().toJson(list)
    }
}