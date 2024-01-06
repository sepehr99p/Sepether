package com.example.sepether.ui.weather.components.forecast.daily

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.domain.entities.ForecastInfo
import com.example.sepether.data.DataState
import com.example.sepether.systemDesign.components.LoadingView
import com.example.sepether.systemDesign.components.RetryView
import com.example.sepether.systemDesign.theme.Primary
import com.example.sepether.ui.weather.WeatherViewModel
import com.example.sepether.utils.isNotToday
import java.text.SimpleDateFormat

@Composable
fun DailyForecast(forecast: DataState<ForecastInfo?>, viewModel: WeatherViewModel) {

    forecast.data?.let { forecastInfo ->
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary), state = rememberLazyListState()
        ) {
            for (i in 0 until forecastInfo.time.size) {
                if (isNotToday(forecastInfo.time[i])) {
                    item(key = forecastInfo.time[i]) {
                        DailyForecastItem(
                            state = rememberDailyForecastState(forecastInfo = forecastInfo, index = i)
                        )
                    }
                }
            }
        }
    } ?: run {
        if (forecast.isLoading) {
            LoadingView()
        } else {
            RetryView(text = "failed to fetch forecast") {
                viewModel.getForecast()
            }
        }
    }

}


