package com.balsha.forecasttask.data.database.converters

import androidx.room.TypeConverter
import com.balsha.forecasttask.data.model.city.CityModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CitiesConverter {
    @TypeConverter
    fun fromString(value: String): ArrayList<CityModel> {
        val listType = object : TypeToken<ArrayList<CityModel>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<CityModel>): String {
        return Gson().toJson(list)
    }
}