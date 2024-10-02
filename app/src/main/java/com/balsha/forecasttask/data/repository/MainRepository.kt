package com.balsha.forecasttask.data.repository

import com.balsha.forecasttask.api.ApiService
import com.balsha.forecasttask.data.database.ForecastDao
import com.balsha.forecasttask.data.model.city.CityModel
import com.balsha.forecasttask.data.model.forecast.ForecastModel
import com.balsha.forecasttask.data.model.forecast.ForecastResponse

interface MainRepository {
    suspend fun addCitiesToDB(cities: ArrayList<CityModel>)
    suspend fun getCitiesFromDB(): List<CityModel>

    suspend fun getForecastFromApi(lat: Double, lon: Double): ForecastResponse

    suspend fun addForecastToDB(forecast: ArrayList<ForecastModel>)
    suspend fun getForecastFromDB(): List<ForecastModel>
}

class MainRepositoryImpl(private val apiService: ApiService, private val forecastDao: ForecastDao) :
    MainRepository {
    override suspend fun addCitiesToDB(cities: ArrayList<CityModel>) =
        forecastDao.insertCities(cities)

    override suspend fun getCitiesFromDB() = forecastDao.retrieveCities()

    override suspend fun getForecastFromApi(lat: Double, lon: Double) =
        apiService.getForecast(lat, lon)

    override suspend fun addForecastToDB(forecast: ArrayList<ForecastModel>) =
        forecastDao.insertForecasts(forecast)

    override suspend fun getForecastFromDB() = forecastDao.retrieveForecasts()
}