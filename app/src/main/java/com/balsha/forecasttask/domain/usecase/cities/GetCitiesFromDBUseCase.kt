package com.balsha.forecasttask.domain.usecase.cities

import com.balsha.forecasttask.data.model.city.CityModel
import com.balsha.forecasttask.data.model.forecast.ForecastModel
import com.balsha.forecasttask.data.repository.MainRepository
import javax.inject.Inject

class GetCitiesFromDBUseCase @Inject constructor(private val repository: MainRepository) {

    suspend fun execute(): List<CityModel> {
        return repository.getCitiesFromDB()
    }
}