package com.balsha.forecasttask.data.model.forecast

import com.balsha.forecasttask.data.model.base.BaseResponse

data class ForecastResponse(
    val cnt: Float,
    val list: List<ForecastModel>,
    val city: City
) : BaseResponse()