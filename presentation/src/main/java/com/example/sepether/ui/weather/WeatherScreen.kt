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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sepether.ui.components.LoadingView
import com.example.sepether.ui.components.RetryView
import com.example.sepether.ui.theme.Primary
import com.example.sepether.ui.weather.components.forecast.daily.DailyForecast
import com.example.sepether.ui.weather.components.forecast.hourly.HourlyForecast
import com.example.sepether.ui.weather.components.today.Today

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {

    val currentWeatherState = viewModel.currentWeather
    val forecastState = viewModel.forecast
    LaunchedEffect(currentWeatherState) {
        currentWeatherState.value
        forecastState.value
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Primary),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        currentWeatherState.value.data?.let {weatherInfo ->
            Spacer(modifier = Modifier.height(16.dp))

            weatherInfo.currentWeatherData?.let {
                Today(it)
            }
            HourlyForecast(weatherInfo = weatherInfo)
            Spacer(modifier = Modifier.height(8.dp))
            DailyForecast(forecastState.value,viewModel)
        } ?: run {
            if (currentWeatherState.value.isLoading) {
                LoadingView()
            } else {
                RetryView(text = "Failed to fetch data") {
                    viewModel.getCurrentWeather()
                }
            }
        }
    }
}
