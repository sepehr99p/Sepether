package com.example.sepether.ui.weather.components.forecast.daily

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.domain.entities.Forecastday
import com.example.sepether.ui.weather.components.SimpleText

@Composable
fun DailyForecastItem(forecastday: Forecastday) {

    SimpleText(value = forecastday.day.avgtemp_c.toString())
    Spacer(modifier = Modifier.height(4.dp))
    Divider(modifier = Modifier.height(2.dp))

}