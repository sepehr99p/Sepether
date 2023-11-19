package com.example.sepether.ui.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sepether.ui.theme.Primary
import com.example.sepether.ui.weather.components.forecast.daily.DailyForecast
import com.example.sepether.ui.weather.components.forecast.hourly.HourlyForecast
import com.example.sepether.ui.weather.components.today.Today

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {

    val currentWeather = viewModel.currentWeather.value
    val forecast = viewModel.forecast
    LaunchedEffect(currentWeather) {
        currentWeather.currentWeatherData
        forecast.value
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Primary),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        currentWeather.currentWeatherData?.let {
            Today(it)
        }
        HourlyForecast(weatherInfo = currentWeather)
        Spacer(modifier = Modifier.height(8.dp))
        DailyForecast(forecast.value)
    }
}
