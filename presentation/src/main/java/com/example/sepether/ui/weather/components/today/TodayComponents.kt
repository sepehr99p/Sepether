package com.example.sepether.ui.weather.components.today

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.entities.WeatherData
import com.example.domain.entities.WeatherInfo
import com.example.sepether.R
import com.example.sepether.systemDesign.theme.Shapes
import com.example.sepether.systemDesign.theme.dimen.corner_16
import com.example.sepether.systemDesign.theme.dimen.image_24
import com.example.sepether.systemDesign.theme.dimen.padding_16
import com.example.sepether.systemDesign.theme.dimen.padding_32
import com.example.sepether.systemDesign.theme.dimen.padding_4
import com.example.sepether.systemDesign.theme.dimen.padding_8
import com.example.sepether.ui.weather.components.SimpleText
import com.example.sepether.utils.WeatherType
import com.example.sepether.utils.dayOfWeek
import com.example.sepether.utils.extensions.airQualityBackground
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt


@Composable
fun ColumnScope.Today(currentWeatherData: WeatherData) {

    SimpleText(
        value = "Today ${
            currentWeatherData.time.format(
                DateTimeFormatter.ofPattern("HH:mm")
            )
        }"
    )
    Spacer(modifier = Modifier.height(padding_16))
    Image(
        painter = painterResource(id = WeatherType.fromWMO(currentWeatherData.weatherType).iconRes),
        contentDescription = null,
        modifier = Modifier.width(150.dp)
    )
    Spacer(modifier = Modifier.height(padding_16))
    Text(
        text = "${currentWeatherData.temperatureCelsius}°C",
        fontSize = 50.sp,
        color = MaterialTheme.colorScheme.onPrimary
    )
    Spacer(modifier = Modifier.height(padding_16))
    Text(
        text = WeatherType.fromWMO(currentWeatherData.weatherType).weatherDesc,
        fontSize = 20.sp,
        color = MaterialTheme.colorScheme.onPrimary
    )
    Spacer(modifier = Modifier.height(padding_32))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        WeatherInfoItem(
            value = currentWeatherData.pressure.roundToInt(),
            unit = "hpa",
            icon = ImageVector.vectorResource(id = R.drawable.ic_pressure),
            iconTint = MaterialTheme.colorScheme.onPrimary,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary)
        )
        WeatherInfoItem(
            value = currentWeatherData.humidity.roundToInt(),
            unit = "%",
            icon = ImageVector.vectorResource(id = R.drawable.ic_drop),
            iconTint = MaterialTheme.colorScheme.onPrimary,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary)
        )
        WeatherInfoItem(
            value = currentWeatherData.windSpeed.roundToInt(),
            unit = "km/h",
            icon = ImageVector.vectorResource(id = R.drawable.ic_wind),
            iconTint = MaterialTheme.colorScheme.onPrimary,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary)
        )
    }
}


@Composable
fun WeatherInfoItem(
    value: Int,
    unit: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(),
    iconTint: Color = MaterialTheme.colorScheme.onPrimary
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(image_24)
        )
        Spacer(modifier = Modifier.width(padding_4))
        Text(
            text = "$value$unit",
            style = textStyle
        )
    }
}

@Composable
fun TodayDetails(weatherInfo: WeatherInfo) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding_8)
    ) {
        Text(
            text = stringResource(id = R.string.daily_detail),
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 25.sp
        )
        Spacer(modifier = Modifier.height(padding_8))
        LazyRow {
            weatherInfo.weatherDataPerDay.forEach { day ->
                item {
                    EachDayDetails(day)
                }
            }
        }
    }

}

@Composable
private fun EachDayDetails(weatherData: Map.Entry<Int, List<WeatherData>>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding_4)
            .clip(shape = Shapes.large)
            .airQualityBackground(),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            modifier = Modifier.padding(padding_8),
            text = dayOfWeek(weatherData.value[0].time.toString()),
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        var averagePressure = 0.0
        var averageHumidity = 0.0
        var averageWindSpeed = 0.0
        var averageTemp = 0.0
        weatherData.value.forEach {
            averagePressure += it.pressure
            averageHumidity += it.humidity
            averageWindSpeed += it.windSpeed
            averageTemp += it.temperatureCelsius
        }
        DetailComponent(info = "Pressure ${(averagePressure / weatherData.value.size).roundToInt()} hpa")
        DetailComponent(info = "Humidity ${(averageHumidity / weatherData.value.size).roundToInt()} %")
        DetailComponent(info = "Wind Speed ${(averageWindSpeed / weatherData.value.size).roundToInt()} km/h")
        DetailComponent(info = "temperatureCelsius ${(averageTemp / weatherData.value.size).roundToInt()} c")
    }
}

@Composable
private fun DetailComponent(info: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding_4)
            .clip(shape = RoundedCornerShape(corner_16, corner_16, corner_16, corner_16))
            .airQualityBackground()
    ) {
        Text(
            text = info,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(padding_16)
        )
    }
}

