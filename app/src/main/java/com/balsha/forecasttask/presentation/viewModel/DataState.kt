package com.balsha.forecasttask.presentation.viewModel

import com.balsha.forecasttask.data.model.city.CitiesResponse
import com.balsha.forecasttask.data.model.city.CityModel
import com.balsha.forecasttask.data.model.forecast.ForecastModel
import com.balsha.forecasttask.data.model.forecast.ForecastResponse

sealed class ForecastsDataState {
    data object Loading : ForecastsDataState()
    data class Success(val data: ForecastResponse) : ForecastsDataState()
    data class Error(val message: String) : ForecastsDataState()
    data class Cached(val data: List<ForecastModel>) : ForecastsDataState()
}

sealed class CitiesDataState {
    data object Loading : CitiesDataState()
    data class Success(val data: CitiesResponse) : CitiesDataState()
    data class Error(val message: String) : CitiesDataState()
    data class Cached(val data: List<CityModel>) : CitiesDataState()
}