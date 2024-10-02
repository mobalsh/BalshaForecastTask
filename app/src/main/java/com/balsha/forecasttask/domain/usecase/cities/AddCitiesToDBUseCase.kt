package com.balsha.forecasttask.domain.usecase.cities

import com.balsha.forecasttask.data.model.city.CityModel
import com.balsha.forecasttask.data.repository.MainRepository
import javax.inject.Inject

class AddCitiesToDBUseCase @Inject constructor(private val repository: MainRepository) {

    suspend fun execute(cities: ArrayList<CityModel>) {
        repository.addCitiesToDB(cities)
    }
}