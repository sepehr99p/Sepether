package com.example.sepether.ui.weather.components.forecast.daily

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.domain.entities.ForecastInfo
import com.example.sepether.utils.WeatherType
import com.example.sepether.utils.dayOfWeek

interface DailyForecastState {
    val time: String
    val maxTemp: Double
    val minTemp: Double
    val iconRes: Int
}

class DailyForecastUiState(private val initialValue: ForecastInfo, private val index: Int) :
    DailyForecastState {
    override val time: String
        get() = dayOfWeek(initialValue.time[index])
    override val maxTemp: Double
        get() = initialValue.maxTemperatures[index]
    override val minTemp: Double
        get() = initialValue.minTemperatures[index]
    override val iconRes: Int
        get() = WeatherType.fromWMO(initialValue.weatherCodes[index]).iconRes

}

@Composable
fun rememberDailyForecastState(forecastInfo: ForecastInfo, index: Int): DailyForecastState =
    remember {
        DailyForecastUiState(forecastInfo, index)
    }