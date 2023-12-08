package com.example.sepether.ui.weather.components.today

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.entities.WeatherData
import com.example.domain.entities.WeatherInfo
import com.example.sepether.R
import com.example.sepether.ui.theme.onPrimary
import com.example.sepether.ui.theme.primaryContainer
import com.example.sepether.ui.weather.components.SimpleText
import com.example.sepether.utils.WeatherType
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt


@Composable
fun Today(currentWeatherData: WeatherData) {

    SimpleText(
        value = "Today ${
            currentWeatherData.time.format(
                DateTimeFormatter.ofPattern("HH:mm")
            )
        }"
    )
    Spacer(modifier = Modifier.height(16.dp))
    Image(
        painter = painterResource(id = WeatherType.fromWMO(currentWeatherData.weatherType).iconRes),
        contentDescription = null,
        modifier = Modifier.width(150.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = "${currentWeatherData.temperatureCelsius}°C",
        fontSize = 50.sp,
        color = onPrimary
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = WeatherType.fromWMO(currentWeatherData.weatherType).weatherDesc,
        fontSize = 20.sp,
        color = onPrimary
    )
    Spacer(modifier = Modifier.height(32.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        WeatherInfoItem(
            value = currentWeatherData.pressure.roundToInt(),
            unit = "hpa",
            icon = ImageVector.vectorResource(id = R.drawable.ic_pressure),
            iconTint = onPrimary,
            textStyle = TextStyle(color = onPrimary)
        )
        WeatherInfoItem(
            value = currentWeatherData.humidity.roundToInt(),
            unit = "%",
            icon = ImageVector.vectorResource(id = R.drawable.ic_drop),
            iconTint = onPrimary,
            textStyle = TextStyle(color = onPrimary)
        )
        WeatherInfoItem(
            value = currentWeatherData.windSpeed.roundToInt(),
            unit = "km/h",
            icon = ImageVector.vectorResource(id = R.drawable.ic_wind),
            iconTint = onPrimary,
            textStyle = TextStyle(color = onPrimary)
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
    iconTint: Color = onPrimary
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(25.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
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
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = "Today's info", color = onPrimary, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        DetailComponent(info = "Pressure ${weatherInfo.currentWeatherData?.pressure}")
        DetailComponent(info = "Humidity ${weatherInfo.currentWeatherData?.humidity}")
        DetailComponent(info = "Wind Speed ${weatherInfo.currentWeatherData?.windSpeed}")
        DetailComponent(info = "temperatureCelsius ${weatherInfo.currentWeatherData?.temperatureCelsius}")
    }

}

@Composable
fun DetailComponent(info: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clip(shape = RoundedCornerShape(16.dp, 16.dp, 16.dp, 16.dp))
            .background(primaryContainer)
    ) {
        Text(
            text = info,
            color = onPrimary,
            modifier = Modifier.padding(16.dp)
        )
    }
}
