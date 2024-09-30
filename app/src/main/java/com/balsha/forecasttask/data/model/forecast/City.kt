package com.balsha.forecasttask.data.model.forecast

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class City(
    val id: Int,
    val name: String,
    val coord: Coord,
    val country: String,
    val population: Float,
    val timezone: Float,
    val sunrise: Float,
    val sunset: Float
) : Parcelable