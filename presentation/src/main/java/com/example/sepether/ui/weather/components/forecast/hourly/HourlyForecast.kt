package com.example.sepether.ui.weather.components.forecast.hourly

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.entities.WeatherInfo
import com.example.sepether.utils.Constants.TIME_PATTERN
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
@Composable
fun HourlyForecast(
    weatherInfo: WeatherInfo,
    modifier: Modifier = Modifier
) {
    weatherInfo.weatherDataPerDay[0]?.let { data ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow(content = {
                data.forEach { weatherData ->
                    item(key = weatherData.time.toString()) {
                        val date = SimpleDateFormat(TIME_PATTERN).parse(weatherData.time.toString())
                        date?.let {
                            if (it.time > System.currentTimeMillis()) {
                                HourlyForecastItem(
                                    state = rememberHourlyState(weatherData = weatherData),
                                    modifier = Modifier
                                        .height(100.dp)
                                        .padding(horizontal = 16.dp)
                                )
                            }
                        }
                    }
                }
            })
        }
    }
}