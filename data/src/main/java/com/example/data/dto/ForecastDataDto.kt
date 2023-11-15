package com.example.data.dto

import com.google.gson.annotations.SerializedName

data class ForecastDataDto(
    val time: List<String>,

    @SerializedName("temperature_2m_max")
    val maxTemperatures: List<Double>,
    @SerializedName("temperature_2m_min")
    val minTemperatures: List<Double>,
    @SerializedName("weathercode")
    val weatherCodes: List<Int>,
    @SerializedName("sunrise")
    val sunrise: List<String>,
    @SerializedName("sunset")
    val sunset: List<String>,
    @SerializedName("rain_sum")
    val rainSum: List<Double>,
    @SerializedName("showers_sum")
    val showersSum: List<Double>,
    @SerializedName("snowfall_sum")
    val snowfallSum: List<Double>

)