package com.example.sepether.ui.weather.components.forecast.daily

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.sepether.ui.theme.primaryContainer
import com.example.sepether.ui.weather.components.SimpleText
import com.example.sepether.utils.WeatherType
import java.text.SimpleDateFormat

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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(shape = RoundedCornerShape(8.dp, 8.dp, 8.dp, 8.dp))
            .background(primaryContainer)
    ) {
        SimpleText(value = dayOfWeek(time))
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

fun dayOfWeek(time: String): String {
    return SimpleDateFormat("EEEE").format(SimpleDateFormat("yyyy-mm-dd").parse(time))
}
