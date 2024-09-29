package com.balsha.forecasttask.data.model.city

import com.balsha.forecasttask.data.model.base.BaseResponse

data class CitiesResponse(val cities: ArrayList<CityModel> = arrayListOf()) : BaseResponse()
