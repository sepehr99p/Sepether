package com.example.data.entities

import com.example.data.common.Astro
import com.example.data.common.Day
import com.example.data.common.Hour

data class Forecastday(
    val astro: Astro,
    val date: String,
    val date_epoch: Int,
    val day: Day,
    val hour: List<Hour>
)
