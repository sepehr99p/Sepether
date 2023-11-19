package com.example.sepether.ui.weather.components.forecast.daily

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.sepether.ui.weather.components.SimpleText
import com.example.sepether.utils.WeatherType

@Composable
fun DailyForecastItem(
    time: String,
    maxTemperatures: Double,
    minTemperatures: Double,
    rainSum: Double,
    showersSum: Double,
    snowfallSum: Double,
    sunrise: String,
    sunset: String,
    weatherCodes: Int?
) {

    Column {
        SimpleText(value = time)
        SimpleText(value = maxTemperatures.toString())
        SimpleText(value = minTemperatures.toString())
        SimpleText(value = rainSum.toString())
        SimpleText(value = showersSum.toString())
        SimpleText(value = snowfallSum.toString())
        SimpleText(value = sunrise)
        SimpleText(value = sunset)
        weatherCodes?.let {
            Image(
                painter = painterResource(id = WeatherType.fromWMO(it).iconRes),
                contentDescription = null,
                modifier = Modifier.width(40.dp)
            )
        }
    }

}

