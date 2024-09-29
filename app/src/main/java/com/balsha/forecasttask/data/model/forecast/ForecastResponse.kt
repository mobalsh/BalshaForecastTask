package com.balsha.forecasttask.data.model.forecast

import com.balsha.forecasttask.data.model.base.BaseResponse

data class ForecastResponse(val cities: ArrayList<ForecastModel> = arrayListOf()) : BaseResponse()
