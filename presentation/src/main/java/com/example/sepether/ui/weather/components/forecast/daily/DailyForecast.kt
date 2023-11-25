package com.example.sepether.ui.weather.components.forecast.daily

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.domain.entities.ForecastInfo
import com.example.sepether.data.DataState
import com.example.sepether.ui.components.LoadingView
import com.example.sepether.ui.theme.Primary
import java.text.SimpleDateFormat

@Composable
fun DailyForecast(forecast: DataState<ForecastInfo?>) {


    val scrollState = rememberLazyListState()
    forecast.data?.let { forecastInfo ->
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(Primary), state = scrollState
        ) {

            for (i in 0 until forecastInfo.time.size) {
                if (isNotToday(forecastInfo.time[i])) {
                    item {
                        DailyForecastItem(
                            forecastInfo.time[i],
                            forecastInfo.maxTemperatures[i],
                            forecastInfo.minTemperatures[i],
                            forecastInfo.weatherCodes[i]
                        )
                    }
                }
            }
        }
    } ?: run {
        if (forecast.isLoading) {
            LoadingView()
        } else {
            // presentError View
        }
    }


}

fun isNotToday(time: String): Boolean =
    SimpleDateFormat("yyyy-MM-dd").parse(time).time > System.currentTimeMillis()
