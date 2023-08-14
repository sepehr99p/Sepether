package com.example.domain.entities.responses

import com.example.domain.entities.CurrentEntity
import com.example.domain.entities.ForecastEntity
import com.example.domain.entities.LocationEntity

data class ForecastServerEntity(
    val current: CurrentEntity,
    val forecast: ForecastEntity,
    val location: LocationEntity
)
