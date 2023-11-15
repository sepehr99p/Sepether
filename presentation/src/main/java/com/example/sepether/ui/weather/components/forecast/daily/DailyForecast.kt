package com.example.sepether.ui.weather.components.forecast.daily

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.sepether.ui.theme.Primary
import com.example.sepether.ui.weather.WeatherViewModel

@Composable
fun DailyForecast(viewModel: WeatherViewModel) {

    val forecast = viewModel.forecast.value
    LaunchedEffect(forecast) {
        forecast.forecast
    }
    val scrollState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(Primary), state = scrollState
    ) {
        forecast.forecast?.forecastday?.let {list ->
            list.forEach {
                item {
                    DailyForecastItem(it)
                }
            }
        }
    }

}