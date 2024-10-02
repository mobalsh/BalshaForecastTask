package com.balsha.forecasttask.presentation

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.balsha.forecasttask.R
import com.balsha.forecasttask.data.model.city.CityModel
import com.balsha.forecasttask.databinding.ActivityMainBinding
import com.balsha.forecasttask.presentation.adapters.CitiesAdapter
import com.balsha.forecasttask.presentation.adapters.ForecastsAdapter
import com.balsha.forecasttask.presentation.viewModel.CitiesDataState
import com.balsha.forecasttask.presentation.viewModel.ForecastsDataState
import com.balsha.forecasttask.presentation.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mContext by lazy { this@MainActivity }

    private lateinit var mBinding: ActivityMainBinding

    private val mMainViewModel: MainViewModel by viewModels()

    private var mCitiesDialog: SelectCityDialog? = null

    private var mSelectedCity: CityModel? = null

    private var mLastAPI = "cities"

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

        mBinding.btnMainForecastRetry.setOnClickListener {
            if (mLastAPI == "cities") mMainViewModel.fetchCities()
            else {
                if (mSelectedCity != null) mMainViewModel.getForecast(
                    mSelectedCity!!.id, mSelectedCity!!.lat, mSelectedCity!!.lon
                ) else Toast.makeText(
                    mContext, getString(R.string.please_select_city_first), Toast.LENGTH_SHORT
                ).show()
            }
        }

        observeGetCities()
        observeGetForecast()
    }

    private fun observeGetCities() {
        mMainViewModel.citiesState.observe(mContext) { response ->
            when (response) {
                is CitiesDataState.Loading -> {
                    showHideProgress(isHidden = true)
                }

                is CitiesDataState.Success -> {
                    showHideProgress(isHidden = false)

                    setupSpinner(response.data.cities)

                    mBinding.linMainCitySelect.visibility = VISIBLE
                    mBinding.tvMainForecastCached.visibility = GONE
                    mBinding.rvMainForecastData.visibility = GONE
                    mBinding.tvMainForecastError.visibility = GONE
                    mBinding.tvMainForecastSearch.visibility = VISIBLE
                    mBinding.btnMainForecastRetry.visibility = GONE
                }

                is CitiesDataState.Cached -> {
                    showHideProgress(isHidden = false)

                    setupSpinner(response.data)

                    mBinding.linMainCitySelect.visibility = VISIBLE
                    mBinding.tvMainForecastCached.visibility = GONE
                    mBinding.rvMainForecastData.visibility = GONE
                    mBinding.tvMainForecastError.visibility = GONE
                    mBinding.tvMainForecastSearch.visibility = VISIBLE
                    mBinding.btnMainForecastRetry.visibility = GONE
                }

                is CitiesDataState.Error -> {
                    showHideProgress(isHidden = false)

                    mBinding.linMainCitySelect.visibility = GONE
                    mBinding.tvMainForecastCached.visibility = GONE
                    mBinding.rvMainForecastData.visibility = GONE
                    mBinding.tvMainForecastError.visibility = VISIBLE
                    mBinding.tvMainForecastSearch.visibility = GONE
                    mBinding.btnMainForecastRetry.visibility = VISIBLE
                }
            }
        }
    }

    private fun observeGetForecast() {
        mMainViewModel.forecastState.observe(mContext) { response ->
            when (response) {
                is ForecastsDataState.Loading -> {
                    showHideProgress(isHidden = true)
                }

                is ForecastsDataState.Success -> {
                    showHideProgress(isHidden = false)

                    val forecasts = response.data.list
                    val adapter = ForecastsAdapter()
                    adapter.setItems(forecasts)
                    mBinding.rvMainForecastData.adapter = adapter

                    mBinding.linMainCitySelect.visibility = VISIBLE
                    mBinding.tvMainForecastCached.visibility = GONE
                    mBinding.rvMainForecastData.visibility = VISIBLE
                    mBinding.tvMainForecastError.visibility = GONE
                    mBinding.tvMainForecastSearch.visibility = GONE
                    mBinding.btnMainForecastRetry.visibility = GONE
                }

                is ForecastsDataState.Cached -> {
                    showHideProgress(isHidden = false)

                    val forecasts = response.data
                    val adapter = ForecastsAdapter()
                    adapter.setItems(forecasts)
                    mBinding.rvMainForecastData.adapter = adapter

                    mBinding.linMainCitySelect.visibility = VISIBLE
                    mBinding.tvMainForecastCached.visibility = VISIBLE
                    mBinding.rvMainForecastData.visibility = VISIBLE
                    mBinding.tvMainForecastError.visibility = GONE
                    mBinding.tvMainForecastSearch.visibility = GONE
                    mBinding.btnMainForecastRetry.visibility = GONE
                }

                is ForecastsDataState.Error -> {
                    showHideProgress(isHidden = false)

                    mBinding.linMainCitySelect.visibility = VISIBLE
                    mBinding.tvMainForecastCached.visibility = GONE
                    mBinding.rvMainForecastData.visibility = GONE
                    mBinding.tvMainForecastError.visibility = VISIBLE
                    mBinding.tvMainForecastSearch.visibility = GONE
                    mBinding.btnMainForecastRetry.visibility = VISIBLE
                }
            }
        }
    }

    private fun setupSpinner(cities: List<CityModel>) {
        val citiesName = arrayListOf(
            CityModel(
                id = -1, cityNameAr = "اختر مدينة", cityNameEn = "Select city", lat = 0.0, lon = 0.0
            )
        )
        citiesName.addAll(cities)

        if (mCitiesDialog != null) {
            mCitiesDialog?.setItems(citiesName)

            mCitiesDialog?.setOnCitySelectedListener(object : CitiesAdapter.IOnCitySelected {
                override fun setOnCitySelected(position: Int) {
                    if (position > 0) {
                        mSelectedCity = citiesName[position]

                        val lang = Locale.getDefault().language
                        mBinding.tvMainCitySelect.text =
                            if (lang == "en") mSelectedCity?.cityNameEn else mSelectedCity?.cityNameAr
                    }
                }
            })

            mBinding.tvMainCitySelect.setOnClickListener { mCitiesDialog?.show() }
        }

        mBinding.btnMainCitySearch.setOnClickListener {
            mLastAPI = "forecasts"
            if (mSelectedCity == null) Toast.makeText(
                mContext, getString(R.string.please_select_city_first), Toast.LENGTH_SHORT
            ).show()
            else fetchWeatherData(mSelectedCity!!)
        }
    }

    private fun fetchWeatherData(city: CityModel) {
        mMainViewModel.getForecast(city.id, city.lat, city.lon)
    }

    private fun showHideProgress(isHidden: Boolean) {
        mBinding.linMainForecastLoader.visibility = VISIBLE.takeIf { isHidden } ?: GONE
        mBinding.pbMainForecastLoader.visibility = VISIBLE.takeIf { isHidden } ?: GONE
    }
}