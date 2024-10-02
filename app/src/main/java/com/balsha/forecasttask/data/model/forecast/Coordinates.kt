package com.balsha.forecasttask.data.model.forecast

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coordinates(val lat: Double, val lon: Double) : Parcelable