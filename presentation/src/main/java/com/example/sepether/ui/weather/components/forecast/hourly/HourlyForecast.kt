package com.example.sepether.ui.weather.components.forecast.hourly

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
import java.text.SimpleDateFormat

@Composable
fun HourlyForecast(
    weatherInfo: WeatherInfo,
    modifier: Modifier = Modifier
) {
    val TAG = "SEPI"
    Log.i(TAG, "WeatherForecast: ")
    weatherInfo.weatherDataPerDay[0]?.let { data ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Today",
                fontSize = 20.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow(content = {
                data.forEach { weatherData ->
                    item {
                        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(weatherData.time.toString())
                        date?.let {
                            if (it.time > System.currentTimeMillis()) {
                                HourlyForecastItem(
                                    weatherData = weatherData,
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