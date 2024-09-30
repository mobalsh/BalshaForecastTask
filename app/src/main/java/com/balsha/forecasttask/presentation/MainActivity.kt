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
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mContext by lazy { this@MainActivity }

    private lateinit var mBinding: ActivityMainBinding

    private val mMainViewModel: MainViewModel by viewModels()

    private var mCitiesDialog: SelectCityDialog? = null

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

        mCitiesDialog = SelectCityDialog(mContext)

        observeGetCities()
        observeGetForecast()
    }

    private fun observeGetCities() {
        mMainViewModel.citiesResponse.observe(mContext) { response ->
            setupSpinner(response)
        }
    }

    private fun observeGetForecast() {
        mMainViewModel.forecastResponse.observe(mContext) { response ->
            Toast.makeText(
                mContext, "Visibility: ${response.list.first().visibility}", Toast.LENGTH_SHORT
            ).show()
        }

        mMainViewModel.forecastError.observe(mContext) { response ->
            Toast.makeText(
                mContext, "Forecast Error: ${response.message}", Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setupSpinner(citiesResponse: CitiesResponse) {
        val citiesName = arrayListOf(
            CityModel(
                id = -1, cityNameAr = "اختر مدينة", cityNameEn = "Select city", lat = 0.0, lon = 0.0
            )
        )
        citiesName.addAll(citiesResponse.cities)

        if (mCitiesDialog != null) {
            mCitiesDialog?.setItems(citiesName)

            mCitiesDialog?.setOnCitySelectedListener(object : CitiesAdapter.IOnCitySelected {
                override fun setOnCitySelected(position: Int) {
                    mSelectedCity = citiesName[position]

                    val lang = Locale.getDefault().language
                    mBinding.tvMainCitySelect.text =
                        if (lang == "en") mSelectedCity.cityNameEn else mSelectedCity.cityNameAr
                }
            })

            mBinding.tvMainCitySelect.setOnClickListener { mCitiesDialog?.show() }
        }

        mBinding.btnMainCitySearch.setOnClickListener {
            fetchWeatherData(mSelectedCity)
        }
    }

    private fun fetchWeatherData(city: CityModel) {
        mMainViewModel.getForecast(city.lat, city.lon)
    }
}