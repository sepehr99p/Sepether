package com.example.data.dto.airQuality

import com.google.gson.annotations.SerializedName

data class AirQualityDto (
    val latitude : Float,
    val longitude : Float,
    val elevation : Float,
    @SerializedName("generationtime_ms")
    val generationTimeMs : Float,
    @SerializedName("utc_offset_seconds")
    val utcOffsetSeconds : Long,
    val timezone : String,
    @SerializedName("timezone_abbreviation")
    val timezoneAbbreviation : String,
    val hourly : HourlyAirQuality
)