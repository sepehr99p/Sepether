package com.example.sepether.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
        val scrollState = rememberLazyListState()
        LaunchedEffect(forecastWeather) {
            forecastWeather.forecast
        }
        LazyColumn(modifier = Modifier
            .fillMaxWidth(), state = scrollState ) {
            forecastWeather.forecast?.forecastday?.let { list ->
                list.forEach {
                    item {
                        ForecastView(forecastday = it)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCurrentWeather()
        viewModel.getForecast()
    }


}