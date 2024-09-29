package com.balsha.forecasttask.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.balsha.forecasttask.data.model.city.CitiesResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainViewModel() : ViewModel() {
    private val _citiesResponse = MutableLiveData<CitiesResponse>()
    val citiesResponse: LiveData<CitiesResponse> = _citiesResponse

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
}