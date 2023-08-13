package com.example.data.entities.response

import com.example.data.entities.Current
import com.example.data.entities.Location

data class CurrentResponse(
    val current: Current,
    val location: Location
)
