package com.example.data.dto.airQuality

data class HourlyAirQuality(
    val time : List<String>,
    val pm10 : List<Float>
)
