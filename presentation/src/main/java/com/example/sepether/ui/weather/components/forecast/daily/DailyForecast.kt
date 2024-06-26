package com.example.sepether.ui.weather.components.forecast.daily

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.domain.entities.ForecastInfo
import com.example.sepether.R
import com.example.sepether.ui.DataState
import com.example.sepether.designSystem.components.LoadingComponent
import com.example.sepether.designSystem.components.WeatherRetryView
import com.example.sepether.ui.weather.WeatherViewModel
import com.example.sepether.utils.isNotToday

@Composable
fun ColumnScope.DailyForecast(forecast: DataState<ForecastInfo?>, viewModel: WeatherViewModel) {

    when(forecast) {
        is DataState.FailedState -> {
            WeatherRetryView(text = stringResource(id = R.string.failed_forecast)) {
                viewModel.getForecast()
            }
        }
        is DataState.LoadedState -> {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary), state = rememberLazyListState()
            ) {
                for (i in 0 until forecast.data!!.time.size) {
                    if (isNotToday(forecast.data.time[i])) {
                        item(key = forecast.data.time[i]) {
                            DailyForecastItem(
                                state = rememberDailyForecastState(
                                    forecastInfo = forecast.data,
                                    index = i
                                )
                            )
                        }
                    }
                }
            }

        }
        is DataState.LoadingState -> LoadingComponent()
    }

}


