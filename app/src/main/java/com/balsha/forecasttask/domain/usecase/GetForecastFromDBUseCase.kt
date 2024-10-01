package com.balsha.forecasttask.domain.usecase

import com.balsha.forecasttask.data.model.forecast.ForecastModel
import com.balsha.forecasttask.data.repository.MainRepository
import javax.inject.Inject

class GetForecastFromDBUseCase @Inject constructor(private val repository: MainRepository) {

    suspend fun execute(): List<ForecastModel> {
        return repository.getForecastFromDB()
    }
}