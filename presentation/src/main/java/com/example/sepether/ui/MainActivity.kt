package com.example.sepether.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.entities.Forecastday
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
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Today",
                color = Color.Black,
                fontSize = 18.sp
            )
            SimpleText(value = "temp : " + currentWeather.current?.temp_c.toString())
            SimpleText(value = "feels like : " + currentWeather.current?.feelslike_c.toString())
            SimpleText(value = "wind : " + currentWeather.current?.wind_kph.toString() + " km/h")
            Divider(
                modifier = Modifier.height(12.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
            )
            ForecastScreen()
        }
    }

    @Composable
    private fun ForecastScreen() {
        val forecastWeather by viewModel.forecast

        LaunchedEffect(forecastWeather) {
            forecastWeather.forecast
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            forecastWeather.forecast?.forecastday?.let { list ->
                list.forEach {
                    Day(forecastday = it)
                }
            }
        }
    }

    @Composable
    private fun Day(forecastday: Forecastday) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = forecastday.date,
                color = Color.Black,
                fontSize = 18.sp
            )
            Spacer(
                modifier = Modifier.height(4.dp)
            )
            SimpleText(value = " sunset : ${forecastday.astro.sunset}")
            SimpleText(value = " sunrise : ${forecastday.astro.sunrise}")
            SimpleText(value = " average temp : ${forecastday.day.avgtemp_c}")
        }
    }

    @Composable
    private fun SimpleText(value: String) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = value,
            color = Color.Gray
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCurrentWeather()
        viewModel.getForecast()
    }


}