package com.example.data.dto

import com.google.gson.annotations.SerializedName

data class WeatherDataDto(
    val time: List<String>,
    @SerializedName("temperature_2m")
    val temperatures: List<Double>,
    @SerializedName("weathercode")
    val weatherCodes: List<Int>,
    @SerializedName("pressure_msl")
    val pressures: List<Double>,
    @SerializedName("windspeed_10m")
    val windSpeeds: List<Double>,
    @SerializedName("relativehumidity_2m")
    val humidities: List<Double>
)