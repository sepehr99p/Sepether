package com.example.data.entities.response

import com.example.data.entities.Current
import com.example.data.entities.Forecast
import com.example.data.entities.Location

data class ForecastResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)
