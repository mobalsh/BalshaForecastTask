package com.balsha.forecasttask.data.model.forecast

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "forecast_table")
data class ForecastModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @SerializedName("dt")
    val date: Float,
    val main: Main,
    val weather: ArrayList<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Float,
    val pop: Float,
    val sys: Sys,
    @SerializedName("dt_txt")
    val dateText: String
) : Parcelable