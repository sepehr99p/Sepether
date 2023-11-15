package com.example.data.dto

import com.google.gson.annotations.SerializedName

data class ForecastDto(
    @SerializedName("daily")
    val forecastDataDto: ForecastDataDto

)