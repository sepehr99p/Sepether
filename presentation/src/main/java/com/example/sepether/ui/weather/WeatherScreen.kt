package com.example.sepether.ui.weather

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.domain.entities.ForecastInfo
import com.example.domain.entities.WeatherInfo
import com.example.sepether.R
import com.example.sepether.ui.DataState
import com.example.sepether.designSystem.components.LoadingComponent
import com.example.sepether.designSystem.components.WeatherRetryView
import com.example.sepether.designSystem.theme.animation_duration
import com.example.sepether.designSystem.theme.dimen.padding_16
import com.example.sepether.designSystem.theme.dimen.padding_8
import com.example.sepether.ui.weather.components.airQuality.DailyAirQualityComponent
import com.example.sepether.ui.weather.components.airQuality.HourlyAirQualityComponent
import com.example.sepether.ui.weather.components.forecast.daily.DailyForecast
import com.example.sepether.ui.weather.components.forecast.hourly.HourlyForecast
import com.example.sepether.ui.weather.components.graphs.LineGraph
import com.example.sepether.ui.weather.components.today.Today
import com.example.sepether.ui.weather.components.today.TodayDetails


@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    val currentWeatherState = viewModel.currentWeather.collectAsState()
    val forecastState = viewModel.forecast.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AnimatedContent(
            currentWeatherState.value,
            transitionSpec = {
                fadeIn(
                    animationSpec = tween(animation_duration)
                ) togetherWith fadeOut(animationSpec = tween(animation_duration))
            },
            label = "Animated Content"
        ) { targetState ->
            when (targetState) {
                is DataState.LoadingState -> {
                    LoadingComponent()
                }

                is DataState.FailedState -> {
                    WeatherRetryView(text = stringResource(id = R.string.failed_to_fetch)) {
                        viewModel.getCurrentWeather()
                    }
                }

                is DataState.LoadedState -> {
                    WeatherSuccessView(currentWeatherState,forecastState,viewModel)
                }
            }

        }

    }

}

@Composable
fun WeatherSuccessView(
    currentWeatherState: State<DataState<WeatherInfo?>>,
    forecastState: State<DataState<ForecastInfo?>>,
    viewModel: WeatherViewModel
) {
    val airQualityState = viewModel.airQuality.collectAsState()
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(padding_16))

        currentWeatherState.value.data!!.currentWeatherData?.let {
            Today(it)
        }
        HourlyForecast(weatherInfo = currentWeatherState.value.data!!)
        Spacer(modifier = Modifier.height(padding_8))
        DailyForecast(forecastState.value, viewModel)
        LineGraph(forecastInfo = forecastState.value.data)
        Spacer(modifier = Modifier.height(padding_8))
//        TempPieChart(forecastInfo = forecastState.value.data)
        when(airQualityState.value) {
            is DataState.FailedState -> {

            }
            is DataState.LoadedState -> {
                DailyAirQualityComponent(data = airQualityState.value.data)
                HourlyAirQualityComponent(data = airQualityState.value.data)
            }
            is DataState.LoadingState -> {
                LoadingComponent()
            }
        }
        Spacer(modifier = Modifier.height(padding_8))
        TodayDetails(currentWeatherState.value.data!!)

    }

}


