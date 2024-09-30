package com.balsha.forecasttask.data.model.base

import android.os.Parcelable
import com.google.errorprone.annotations.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
open class BaseResponse(
    val cod: Int = -1,
    val code: Int = -1,
    val status: String = "",
    val message: String = ""
) : Parcelable