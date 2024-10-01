package com.balsha.forecasttask.data.database

import androidx.room.TypeConverter
import com.balsha.forecasttask.data.model.forecast.Clouds
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ForecastsCloudsConverter {
    @TypeConverter
    fun fromString(value: String): Clouds {
        val listType = object : TypeToken<Clouds>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: Clouds): String {
        return Gson().toJson(list)
    }
}