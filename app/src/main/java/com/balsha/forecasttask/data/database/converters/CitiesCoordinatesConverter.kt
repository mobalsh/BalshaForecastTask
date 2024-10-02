package com.balsha.forecasttask.data.database.converters

import androidx.room.TypeConverter
import com.balsha.forecasttask.data.model.city.CityModel
import com.balsha.forecasttask.data.model.forecast.Coordinates
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CitiesCoordinatesConverter {
    @TypeConverter
    fun fromString(value: String): Coordinates {
        val listType = object : TypeToken<Coordinates>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: Coordinates): String {
        return Gson().toJson(list)
    }
}