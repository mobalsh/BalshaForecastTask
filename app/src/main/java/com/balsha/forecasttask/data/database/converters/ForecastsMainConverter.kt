package com.balsha.forecasttask.data.database.converters

import androidx.room.TypeConverter
import com.balsha.forecasttask.data.model.forecast.Main
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ForecastsMainConverter {
    @TypeConverter
    fun fromString(value: String): Main {
        val listType = object : TypeToken<Main>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: Main): String {
        return Gson().toJson(list)
    }
}