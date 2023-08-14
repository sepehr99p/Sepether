package com.example.domain.entities

data class Forecastday(
    val astro: AstroEntity,
    val date: String,
    val date_epoch: Int,
    val day: DayEntity,
    val hour: List<HourEntity>
)
