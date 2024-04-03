package com.example.domain.entities

data class HourlyAirQualityEntity(
    val time : List<String>,
    val pm10 : List<Float>
)
