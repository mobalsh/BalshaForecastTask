package com.balsha.forecasttask.data.model.forecast

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Wind(
    val speed: Float,
    val deg: Float,
    val gust: Float
) : Parcelable