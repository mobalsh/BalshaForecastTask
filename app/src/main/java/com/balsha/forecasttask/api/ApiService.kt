package com.balsha.forecasttask.api

import com.balsha.forecasttask.BuildConfig
import com.balsha.forecasttask.data.model.forecast.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("forecast")
    suspend fun getForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String = BuildConfig.APP_ID,
        @Query("units") units: String = "metric"
    ): ForecastResponse
}
