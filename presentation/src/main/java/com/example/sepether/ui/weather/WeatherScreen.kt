package com.example.sepether.ui.weather

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import com.example.sepether.ui.components.LoadingView
import com.example.sepether.ui.components.RetryView
import com.example.sepether.ui.theme.DarkColorScheme
import com.example.sepether.ui.theme.LightColorScheme
import com.example.sepether.ui.weather.components.forecast.daily.DailyForecast
import com.example.sepether.ui.weather.components.forecast.hourly.HourlyForecast
import com.example.sepether.ui.weather.components.graphs.LineGraph
import com.example.sepether.ui.weather.components.today.Today
import com.example.sepether.ui.weather.components.today.TodayDetails


@Composable
fun SepetherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (isSystemInDarkTheme()) dynamicDarkColorScheme(context) else dynamicLightColorScheme(
                context
            )
        }

        isSystemInDarkTheme() -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }


    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}


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
            .background(MaterialTheme.colorScheme.primary)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        currentWeatherState.value.data?.let { weatherInfo ->
            Spacer(modifier = Modifier.height(16.dp))

            weatherInfo.currentWeatherData?.let {
                Today(it)
            }
            HourlyForecast(weatherInfo = weatherInfo)
            Spacer(modifier = Modifier.height(8.dp))
            DailyForecast(forecastState.value, viewModel)
            LineGraph(forecastInfo = forecastState.value.data)
            TodayDetails(weatherInfo)
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


