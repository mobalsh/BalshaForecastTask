package com.balsha.forecasttask.domain.usecase

import com.balsha.forecasttask.data.model.forecast.ForecastResponse
import com.balsha.forecasttask.data.repository.MainRepository
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(private val repository: MainRepository) {

    suspend fun execute(lat: Double, lon: Double): ForecastResponse {
        return repository.getForecast(lat, lon)
    }
}