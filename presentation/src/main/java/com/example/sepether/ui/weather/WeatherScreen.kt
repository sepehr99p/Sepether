package com.example.sepether.ui.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sepether.systemDesign.components.LoadingView
import com.example.sepether.systemDesign.components.RetryView
import com.example.sepether.ui.weather.components.forecast.daily.DailyForecast
import com.example.sepether.ui.weather.components.forecast.hourly.HourlyForecast
import com.example.sepether.ui.weather.components.graphs.LineGraph
import com.example.sepether.ui.weather.components.graphs.TempPieChart
import com.example.sepether.ui.weather.components.today.Today
import com.example.sepether.ui.weather.components.today.TodayDetails


@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {

    val currentWeatherState by remember {
        viewModel.currentWeather
    }
    val forecastState by remember {
        viewModel.forecast
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.primary)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        currentWeatherState.data?.let { weatherInfo ->
            Spacer(modifier = Modifier.height(16.dp))

            weatherInfo.currentWeatherData?.let {
                Today(it)
            }
            HourlyForecast(weatherInfo = weatherInfo)
            Spacer(modifier = Modifier.height(8.dp))
            DailyForecast(forecastState, viewModel)
            LineGraph(forecastInfo = forecastState.data)
            Spacer(modifier = Modifier.height(8.dp))
            TempPieChart(forecastInfo = forecastState.data)
            TodayDetails(weatherInfo)
        } ?: run {
            if (currentWeatherState.isLoading) {
                LoadingView()
            } else {
                RetryView(text = "Failed to fetch data") {
                    viewModel.getCurrentWeather()
                }
            }
        }
    }
}


