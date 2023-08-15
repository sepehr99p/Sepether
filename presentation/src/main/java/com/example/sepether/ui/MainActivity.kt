package com.example.sepether.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<WeatherViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                HomeScreen()
            }
        }
    }

    @Composable
    private fun HomeScreen() {
        val currentWeather by viewModel.currentWeather

        LaunchedEffect(currentWeather) {
            currentWeather.current
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            TextC(value = "temp : "+currentWeather.current?.temp_c.toString())
            TextC(value = "feels like : "+currentWeather.current?.feelslike_c.toString())
            TextC(value = "wind : " +currentWeather.current?.wind_kph.toString() + " km/h")
            ForecastScreen()
        }
    }

    @Composable
    private fun ForecastScreen() {
        val forecastWeather by viewModel.forecast

        LaunchedEffect(forecastWeather) {
            forecastWeather.forecast
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            forecastWeather.forecast?.forecastday?.let {
                it.forEach {
                    TextC("sunset : "+it.astro.sunset)
                }
            }
        }

    }

    @Composable
    private fun TextC(value : String) {
        Text(
            modifier = Modifier
                .padding(16.dp),
            text = value
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCurrentWeather()
        viewModel.getForecast()
    }


}