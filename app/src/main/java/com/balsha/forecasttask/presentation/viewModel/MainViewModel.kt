package com.balsha.forecasttask.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.balsha.forecasttask.data.model.city.CitiesResponse
import com.balsha.forecasttask.domain.usecase.AddForecastToDBUseCase
import com.balsha.forecasttask.domain.usecase.GetForecastFromApiUseCase
import com.balsha.forecasttask.domain.usecase.GetForecastFromDBUseCase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getForecastUseCase: GetForecastFromApiUseCase,
    private val getForecastFromDBUseCase: GetForecastFromDBUseCase,
    private val addForecastToDBUseCase: AddForecastToDBUseCase
) :
    ViewModel() {

    private val _citiesState = MutableLiveData<CitiesDataState>()
    val citiesState: LiveData<CitiesDataState> = _citiesState

    private val _forecastState = MutableLiveData<ForecastsDataState>()
    val forecastState: LiveData<ForecastsDataState> = _forecastState

    init {
        fetchCities()
    }

    private fun fetchCities() {
        viewModelScope.launch {
            _citiesState.value = CitiesDataState.Loading

            try {
                val citiesResponse = withContext(Dispatchers.IO) {
                    val url =
                        URL("https://dev-orcas.s3.eu-west-1.amazonaws.com/uploads/cities.json")
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "GET"

                    val reader = InputStreamReader(connection.inputStream)
                    val response = Gson().fromJson<CitiesResponse>(
                        reader, object : TypeToken<CitiesResponse>() {}.type
                    )
                    reader.close()
                    Log.e("MAIN_VIEW_MODEL", "Cities Response: $response")
                    response
                }

                _citiesState.value = CitiesDataState.Success(citiesResponse)

            } catch (e: IOException) {
                e.printStackTrace()
                _citiesState.value = CitiesDataState.Error("Error")
            }
        }
    }

    fun getForecast(lat: Double, lon: Double) {
        viewModelScope.launch {
            _forecastState.value = ForecastsDataState.Loading
            try {
                val forecastResponse = withContext(Dispatchers.IO) {
                    getForecastUseCase.execute(lat, lon)
                }
                if (forecastResponse.cod == 200) {
                    _forecastState.value = ForecastsDataState.Success(forecastResponse)
                    addForecastToDBUseCase.execute(forecastResponse.list)
                } else {
                    val cachedForecast = withContext(Dispatchers.IO) {
                        getForecastFromDBUseCase.execute()
                    }
                    if (cachedForecast.isNotEmpty())
                        _forecastState.value = ForecastsDataState.Cached(cachedForecast)
                    else _forecastState.value = ForecastsDataState.Error(forecastResponse.message)
                }
            } catch (e: HttpException) {
                if (e.code() == 401) {
                    withContext(Dispatchers.Main) {
                        _forecastState.value = ForecastsDataState.Error("Unauthorized")
                    }
                } else {
                    val cachedForecast = withContext(Dispatchers.IO) {
                        getForecastFromDBUseCase.execute()
                    }
                    if (cachedForecast.isNotEmpty())
                        _forecastState.value = ForecastsDataState.Cached(cachedForecast)
                    else _forecastState.value = ForecastsDataState.Error("Error")
                }
            } catch (e: IOException) {
                e.printStackTrace()
                val cachedForecast = withContext(Dispatchers.IO) {
                    getForecastFromDBUseCase.execute()
                }
                if (cachedForecast.isNotEmpty())
                    _forecastState.value = ForecastsDataState.Cached(cachedForecast)
                else _forecastState.value = ForecastsDataState.Error("Error")
            }
        }
    }


    /*fun fetchForecast(cityName: String) {
        viewModelScope.launch {
            _dataState.value = DataState.Loading // Set loading state

            try {
                // Fetch data from API
                val response = fetchForecastFromApi(cityName) // Implement this function
                response.forEach { repository.insert(it) } // Insert into Room
                _dataState.value = DataState.Success(response)
            } catch (e: Exception) {
                val cachedData = repository.getForecastsByCity(cityName)
                if (cachedData.isNotEmpty()) {
                    _dataState.value = DataState.Cached(cachedData)
                } else {
                    _dataState.value = DataState.Error("Failed to retrieve data. Please try again.")
                }
            }
        }
    }*/
}