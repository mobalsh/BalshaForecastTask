package com.balsha.forecasttask.data.database.converters

import androidx.room.TypeConverter
import com.balsha.forecasttask.data.model.forecast.Sys
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ForecastsSysConverter {
    @TypeConverter
    fun fromString(value: String): Sys {
        val listType = object : TypeToken<Sys>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: Sys): String {
        return Gson().toJson(list)
    }
}