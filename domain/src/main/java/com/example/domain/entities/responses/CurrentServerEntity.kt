package com.example.domain.entities.responses

import com.example.domain.entities.CurrentEntity
import com.example.domain.entities.LocationEntity

data class CurrentServerEntity (
    val current: CurrentEntity,
    val location: LocationEntity
    )