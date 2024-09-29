package com.balsha.forecasttask.data.repository

import com.balsha.forecasttask.api.ApiService
import com.balsha.forecasttask.data.model.forecast.ForecastResponse

interface MainRepository {
    suspend fun getForecast(): ForecastResponse
}

class MainRepositoryImpl(private val apiService: ApiService) : MainRepository {
    override suspend fun getForecast() = apiService.getForecast()
}