package com.balsha.forecasttask.data.database

import androidx.room.TypeConverter
import com.balsha.forecasttask.data.model.forecast.ForecastModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ForecastsConverter {
    @TypeConverter
    fun fromString(value: String): ArrayList<ForecastModel> {
        val listType = object : TypeToken<ArrayList<ForecastModel>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<ForecastModel>): String {
        return Gson().toJson(list)
    }
}