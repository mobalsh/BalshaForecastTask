package com.balsha.forecasttask.data.model.city

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CityModel(
    val id: Int = 1,
    val cityNameAr: String = "القاهرة",
    val cityNameEn: String = "Cairo",
    val lat: Double = 30.0444,
    val lon: Double = 31.2357
) : Parcelable