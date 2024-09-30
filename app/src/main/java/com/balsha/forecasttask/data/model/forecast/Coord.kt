package com.balsha.forecasttask.data.model.forecast

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coord(val lat: Double, val lon: Double) : Parcelable