package com.example.sepether.ui.weather.components.forecast.daily

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.domain.entities.ForecastInfo
import com.example.sepether.ui.theme.Primary

@Composable
fun DailyForecast(forecast : ForecastInfo) {


    val scrollState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(Primary), state = scrollState
    ) {

        for (i in 0 until forecast.time.size) {
            item {
                DailyForecastItem(
                    forecast.time[i],
                    forecast.maxTemperatures[i],
                    forecast.minTemperatures[i],
                    forecast.rainSum[i],
                    forecast.showersSum[i],
                    forecast.snowfallSum[i],
                    forecast.sunrise[i],
                    forecast.sunset[i],
                    forecast.weatherCodes?.get(i)
                )
            }
        }
    }

}