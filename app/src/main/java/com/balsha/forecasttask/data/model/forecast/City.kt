package com.balsha.forecasttask.data.model.forecast

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class City(
    val id: Int,
    val name: String,
    @SerializedName("coord")
    val coordinates: Coordinates,
    val country: String,
    val population: Float,
    val timezone: Float,
    val sunrise: Float,
    val sunset: Float
) : Parcelable