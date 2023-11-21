package com.example.sepether.ui.weather.components.forecast.daily

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.domain.entities.ForecastInfo
import com.example.sepether.ui.theme.Primary
import java.text.SimpleDateFormat

@Composable
fun DailyForecast(forecast : ForecastInfo) {


    val scrollState = rememberLazyListState()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(Primary), state = scrollState
    ) {

        for (i in 0 until forecast.time.size) {
            if (isNotToday(forecast.time[i])) {
                item {
                    DailyForecastItem(
                        forecast.time[i],
                        forecast.maxTemperatures[i],
                        forecast.minTemperatures[i],
                        forecast.weatherCodes[i]
                    )
                }
            }
        }
    }

}

fun isNotToday(time: String): Boolean =
    SimpleDateFormat("yyyy-MM-dd").parse(time).time > System.currentTimeMillis()
