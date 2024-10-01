package com.balsha.forecasttask.data.model.forecast

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Main(
    val temp: Float,
    @SerializedName("feels_like")
    val feelsLike: Float,
    @SerializedName("temp_min")
    val tempMin: Float,
    @SerializedName("temp_max")
    val tempMax: Float,
    val pressure: Float,
    @SerializedName("sea_level")
    val seaLevel: Float,
    @SerializedName("grnd_level")
    val grandLevel: Float,
    val humidity: Float,
    @SerializedName("temp_kf")
    val tempKF: Float
) : Parcelable