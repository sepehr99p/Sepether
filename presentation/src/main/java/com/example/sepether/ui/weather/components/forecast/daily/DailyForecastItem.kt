package com.example.sepether.ui.weather.components.forecast.daily

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sepether.ui.theme.Primary_Blue
import com.example.sepether.ui.theme.Red_Negative
import com.example.sepether.ui.theme.primaryContainer
import com.example.sepether.ui.weather.components.SimpleText
import com.example.sepether.utils.WeatherType
import java.text.SimpleDateFormat
import java.util.Date

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
            .clip(shape = RoundedCornerShape(16.dp, 16.dp, 16.dp, 16.dp))
            .background(primaryContainer),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SimpleText(value = dayOfWeek(time))
        Temperature(maxTemperatures, Red_Negative)
        Temperature(minTemperatures, Primary_Blue)
        SimpleText(value = rainSum.toString())
        SimpleText(value = snowfallSum.toString())
        weatherCodes?.let {
            Image(
                painter = painterResource(id = WeatherType.fromWMO(it).iconRes),
                contentDescription = null,
                modifier = Modifier.width(40.dp)
            )
        }
    }

}

@Composable
fun Temperature(temp: Double, color: Color) {
    Text(
        text = "$temp°C",
        color = color,
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    )
}

fun hour(time: String): String {
    return SimpleDateFormat("HH:mm").format(SimpleDateFormat("yyyy-mm-dd'T'HH:mm").parse(time))
}

fun dayOfWeek(time: String): String {
    return SimpleDateFormat("EEE").format(SimpleDateFormat("yyyy-MM-dd").parse(time))
}
