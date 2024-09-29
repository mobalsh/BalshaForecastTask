package com.balsha.forecasttask.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.balsha.forecasttask.R
import com.balsha.forecasttask.data.model.city.CitiesResponse
import com.balsha.forecasttask.data.model.city.CityModel
import com.balsha.forecasttask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val mContext by lazy { this@MainActivity }

    private lateinit var mBinding: ActivityMainBinding

    private val mMainViewModel: MainViewModel by viewModels()

    private val mCitiesAdapter by lazy { CitiesAdapter(mContext) }

    private var mSelectedCity = CityModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mBinding.spnMainCitySelect.adapter = mCitiesAdapter

        observeGetCities()
    }

    private fun observeGetCities() {
        mMainViewModel.citiesResponse.observe(mContext) { response ->
            setupSpinner(response)
        }
    }

    private fun setupSpinner(citiesResponse: CitiesResponse) {
        val citiesName = arrayListOf(
            CityModel(
                id = -1, cityNameAr = "اختر مدينة", cityNameEn = "Select city", lat = 0.0, lon = 0.0
            )
        )
        citiesName.addAll(citiesResponse.cities)

        mCitiesAdapter.setItems(citiesName)

        mCitiesAdapter.setOnCitySelectedListener(object : CitiesAdapter.IOnCitySelected {
            override fun setOnCitySelected(position: Int) {
                if (position != 0) {
                    mSelectedCity = citiesName[position]
                    mBinding.spnMainCitySelect.setSelection(position)

                    Toast.makeText(
                        mContext, "Selected city: ${mSelectedCity.lat}", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

        mBinding.btnMainCitySearch.setOnClickListener {
            fetchWeatherData(mSelectedCity)
        }
    }

    private fun fetchWeatherData(city: CityModel) {
        // TODO: Implement your API call to fetch weather data for the selected city
        Toast.makeText(mContext, "Fetching weather data for: $city", Toast.LENGTH_SHORT).show()
    }
}