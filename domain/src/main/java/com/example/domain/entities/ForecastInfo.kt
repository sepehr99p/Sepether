package com.example.domain.entities

data class ForecastInfo(
    val time: List<String>,
    val maxTemperatures: List<Double>,
    val minTemperatures: List<Double>,
    val weatherCodes: List<Int>?,
    val sunrise: List<String>,
    val sunset: List<String>,
    val rainSum: List<Double>,
    val showersSum: List<Double>,
    val snowfallSum: List<Double>

)