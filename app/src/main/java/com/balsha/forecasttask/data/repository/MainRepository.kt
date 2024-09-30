package com.balsha.forecasttask.data.repository

import com.balsha.forecasttask.api.ApiService
import com.balsha.forecasttask.data.model.forecast.ForecastResponse

interface MainRepository {
    suspend fun getForecast(lat: Double, lon: Double): ForecastResponse
}

class MainRepositoryImpl(private val apiService: ApiService) : MainRepository {
    override suspend fun getForecast(lat: Double, lon: Double) = apiService.getForecast(lat, lon)
}