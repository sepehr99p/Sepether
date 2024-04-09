package com.example.domain.entities

import com.google.gson.annotations.SerializedName

data class AirQualityEntity(
    val elevation : Float,
    val timezoneAbbreviation : String,
    val hourly : HourlyAirQualityEntity,
    val daily : DailyAirQualityEntity
)
