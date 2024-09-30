package com.balsha.forecasttask.data.model.forecast

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForecastModel(
    val dt: Float,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Float,
    val pop: Float,
    val sys: Sys,
    val dt_txt: String
) : Parcelable