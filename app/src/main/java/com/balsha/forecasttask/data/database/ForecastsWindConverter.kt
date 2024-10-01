package com.balsha.forecasttask.data.database

import androidx.room.TypeConverter
import com.balsha.forecasttask.data.model.forecast.Wind
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ForecastsWindConverter {
    @TypeConverter
    fun fromString(value: String): Wind {
        val listType = object : TypeToken<Wind>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: Wind): String {
        return Gson().toJson(list)
    }
}