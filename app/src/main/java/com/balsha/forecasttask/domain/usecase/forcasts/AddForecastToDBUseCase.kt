package com.balsha.forecasttask.domain.usecase.forcasts

import com.balsha.forecasttask.data.model.forecast.ForecastModel
import com.balsha.forecasttask.data.repository.MainRepository
import javax.inject.Inject

class AddForecastToDBUseCase @Inject constructor(private val repository: MainRepository) {

    suspend fun execute(forecast: ArrayList<ForecastModel>) {
        repository.addForecastToDB(forecast)
    }
}