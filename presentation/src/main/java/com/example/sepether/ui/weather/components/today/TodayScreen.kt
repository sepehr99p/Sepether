package com.example.sepether.ui.weather.components.today

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sepether.ui.theme.Primary
import com.example.sepether.ui.theme.onPrimary
import com.example.sepether.ui.theme.onPrimaryContainer
import com.example.sepether.ui.weather.WeatherViewModel
import com.example.sepether.ui.weather.components.CustomText
import com.example.sepether.ui.weather.components.SimpleText
import com.example.sepether.ui.weather.components.forecast.daily.DailyForecast
import com.example.sepether.utils.extensions.toCelcius

@Composable
fun TodayWeatherScreen(viewModel: WeatherViewModel) {

    val currentWeather = viewModel.currentWeather.value
    LaunchedEffect(currentWeather) {
        currentWeather.current
        currentWeather.location
    }

    currentWeather.current?.is_day?.let {
        // set light background
    } ?: run {
        // set dark background
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
        CustomText(value = currentWeather.location?.name,36, FontWeight.ExtraBold, onPrimary)
        Spacer(modifier = Modifier.height(4.dp))
        CustomText(value = ("${currentWeather.current?.temp_c}c"),44, FontWeight.Light, onPrimary)
        Spacer(modifier = Modifier.height(4.dp))
        CustomText(value = currentWeather.current?.condition?.text,24, FontWeight.Medium, onPrimaryContainer)
        Spacer(modifier = Modifier.height(4.dp))
        SimpleText(value = ("feels like ${currentWeather.current?.feelslike_f?.toCelcius()} C"))
        Spacer(modifier = Modifier.height(8.dp))
        DailyForecast(viewModel)
    }
}
