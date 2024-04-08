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
import com.example.sepether.systemDesign.components.WeatherLoadingView
import com.example.sepether.systemDesign.components.WeatherRetryView
import com.example.sepether.systemDesign.theme.dimen.padding_16
import com.example.sepether.systemDesign.theme.dimen.padding_8
import com.example.sepether.ui.weather.components.forecast.daily.DailyForecast
import com.example.sepether.ui.weather.components.forecast.hourly.HourlyForecast
import com.example.sepether.ui.weather.components.graphs.LineGraph
import com.example.sepether.ui.weather.components.graphs.TempPieChart
import com.example.sepether.ui.weather.components.today.Today
import com.example.sepether.ui.weather.components.today.TodayDetails


@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {

    val currentWeatherState = viewModel.currentWeather.collectAsState()
    val forecastState = viewModel.forecast.collectAsState()
    viewModel.fetchAirQuality()
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
                    animationSpec = tween(500)
                ) togetherWith fadeOut(animationSpec = tween(500))
            },
            label = "Animated Content"
        ) { targetState ->
            when (targetState) {
                is DataState.LoadingState -> {
                    WeatherLoadingView()
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
        TodayDetails(currentWeatherState.value.data!!)
    }

}


