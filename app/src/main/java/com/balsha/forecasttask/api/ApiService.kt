package com.balsha.forecasttask.api

import com.balsha.forecasttask.data.model.forecast.ForecastResponse

interface ApiService {
    suspend fun getForecast(): ForecastResponse
}
