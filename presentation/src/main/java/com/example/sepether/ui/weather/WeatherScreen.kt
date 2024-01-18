package com.example.sepether.ui.weather

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.sepether.data.DataState
import com.example.sepether.systemDesign.components.LoadingView
import com.example.sepether.systemDesign.components.RetryView
import com.example.sepether.ui.weather.components.forecast.daily.DailyForecast
import com.example.sepether.ui.weather.components.forecast.hourly.HourlyForecast
import com.example.sepether.ui.weather.components.graphs.LineGraph
import com.example.sepether.ui.weather.components.graphs.TempPieChart
import com.example.sepether.ui.weather.components.today.Today
import com.example.sepether.ui.weather.components.today.TodayDetails
import kotlin.math.roundToInt


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
            .background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AnimatedContent(
            currentWeatherState,
            transitionSpec = {
                fadeIn(
                    animationSpec = tween(500)
                ) togetherWith fadeOut(animationSpec = tween(500))
            },
            label = "Animated Content"
        ) { targetState ->
            when (targetState) {
                is DataState.LoadingState -> {
                    LoadingView()
                }

                is DataState.FailedState -> {
                    RetryView(text = "Failed to fetch data") {
                        viewModel.getCurrentWeather()
                    }
                }

                is DataState.LoadedState -> {

                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))

                        currentWeatherState.data!!.currentWeatherData?.let {
                            Today(it)
                        }
                        HourlyForecast(weatherInfo = currentWeatherState.data!!)
                        Spacer(modifier = Modifier.height(8.dp))
                        DailyForecast(forecastState, viewModel)
                        LineGraph(forecastInfo = forecastState.data)
                        Spacer(modifier = Modifier.height(8.dp))
                        TempPieChart(forecastInfo = forecastState.data)
                        TodayDetails(currentWeatherState.data!!)
                    }
                }
            }

        }

    }

}


