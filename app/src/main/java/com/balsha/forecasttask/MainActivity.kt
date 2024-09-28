package com.balsha.forecasttask

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private val mContext by lazy { this@MainActivity }

    private lateinit var spnMainCitySelect: Spinner
    private lateinit var btnMainCitySearch: Button
    private var citiesResponse = CitiesResponse()
    private var selectedCity = CityModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        spnMainCitySelect = findViewById(R.id.spnMainCitySelect)
        btnMainCitySearch = findViewById(R.id.btnMainCitySearch)

        // Fetch cities from JSON file
        fetchCities()

        // Set up spinner and button
        setupSpinner()
    }

    // Sample method to get a list of cities
    private fun fetchCities() {
        Thread {
            try {
                val url = URL("https://dev-orcas.s3.eu-west-1.amazonaws.com/uploads/cities.json")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val reader = InputStreamReader(connection.inputStream)
                citiesResponse =
                    Gson().fromJson(reader, object : TypeToken<CitiesResponse>() {}.type)
                reader.close()
                runOnUiThread(mContext::setupSpinner) // Update UI on the main thread
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun setupSpinner() {
        val citiesName = arrayListOf("Select city")
        citiesName.addAll(citiesResponse.cities.map { city -> "${city.id} -> ${city.cityNameAr} | ${city.cityNameEn}" })
        val adapter = ArrayAdapter(mContext, android.R.layout.simple_spinner_item, citiesName)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnMainCitySelect.setAdapter(adapter)

        spnMainCitySelect.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {
                if (position != 0) {
                    val selectedItem = parent.getItemAtPosition(position).toString()
                    val cityId = selectedItem.split("->").first().trim().toInt()
                    selectedCity =
                        citiesResponse.cities.find { city -> city.id == cityId } ?: CityModel()
                    Toast.makeText(
                        mContext,
                        "Selected city: ${selectedCity.lat}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        btnMainCitySearch.setOnClickListener {
            val city: String = spnMainCitySelect.getSelectedItem().toString()
            fetchWeatherData(city)
        }
    }

    // Method to fetch weather data (implementation will vary)
    private fun fetchWeatherData(city: String) {
        // TODO: Implement your API call to fetch weather data for the selected city
        Toast.makeText(mContext, "Fetching weather data for: $city", Toast.LENGTH_SHORT).show()
    }
}