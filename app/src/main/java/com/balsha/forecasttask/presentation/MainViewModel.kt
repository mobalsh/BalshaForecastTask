package com.balsha.forecasttask.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.balsha.forecasttask.data.model.base.BaseResponse
import com.balsha.forecasttask.data.model.city.CitiesResponse
import com.balsha.forecasttask.data.model.forecast.ForecastResponse
import com.balsha.forecasttask.domain.usecase.GetForecastUseCase
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
class MainViewModel @Inject constructor(private val getForecastUseCase: GetForecastUseCase) :
    ViewModel() {

    private val _citiesResponse = MutableLiveData<CitiesResponse>()
    val citiesResponse: LiveData<CitiesResponse> = _citiesResponse

    private val _forecastResponse = MutableLiveData<ForecastResponse>()
    val forecastResponse: LiveData<ForecastResponse> = _forecastResponse

    private val _forecastErrors = MutableLiveData<BaseResponse>()
    val forecastError: LiveData<BaseResponse> = _forecastErrors

    init {
        fetchCities()
    }

    private fun fetchCities() {
        viewModelScope.launch {
            try {
                // Perform network call on IO dispatcher
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

                // Update LiveData on the main thread
                _citiesResponse.value = citiesResponse

            } catch (e: IOException) {
                e.printStackTrace()
                // Handle error, maybe update a LiveData for error state
            }
        }
    }

    fun getForecast(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                // Perform network call on IO dispatcher
                val forecastResponse = withContext(Dispatchers.IO) {
                    getForecastUseCase.execute(lat, lon)
                }
                // Update LiveData on the main thread
                if (forecastResponse.cod != 200)
                    _forecastErrors.value = forecastResponse
                else _forecastResponse.value = forecastResponse
            } catch (e: HttpException) {
                if (e.code() == 401) {
                    withContext(Dispatchers.Main) {
                        _forecastErrors.value = BaseResponse(message = "Unauthorized")
                    }
                } else {
                    _forecastErrors.value = BaseResponse(message = "Error")
                }
            } catch (e: IOException) {
                e.printStackTrace()
                // Handle error, maybe update a LiveData for error state
            }
        }
    }
}