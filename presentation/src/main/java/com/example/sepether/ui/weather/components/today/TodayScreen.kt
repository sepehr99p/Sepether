package com.example.sepether.ui.weather.components.today

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.entities.WeatherData
import com.example.sepether.R
import com.example.sepether.ui.theme.Primary
import com.example.sepether.ui.weather.WeatherViewModel
import com.example.sepether.ui.weather.components.SimpleText
import com.example.sepether.ui.weather.components.forecast.daily.DailyForecast
import com.example.sepether.ui.weather.components.forecast.hourly.HourlyForecast
import com.example.sepether.utils.WeatherType
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {

    val currentWeather = viewModel.currentWeather.value
    val forecast = viewModel.forecast
    LaunchedEffect(currentWeather) {
        currentWeather.currentWeatherData
        forecast.value
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Primary),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        currentWeather.currentWeatherData?.let {
            Today(it)
        }
        HourlyForecast(weatherInfo = currentWeather)
        Spacer(modifier = Modifier.height(8.dp))
        DailyForecast(forecast.value)
    }
}

@Composable
fun Today(currentWeatherData: WeatherData) {

    SimpleText(value = "Today ${
        currentWeatherData.time.format(
            DateTimeFormatter.ofPattern("HH:mm")
        )
    }")
    Spacer(modifier = Modifier.height(16.dp))
    Image(
        painter = painterResource(id = WeatherType.fromWMO(currentWeatherData.weatherType).iconRes),
        contentDescription = null,
        modifier = Modifier.width(200.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = "${currentWeatherData.temperatureCelsius}°C",
        fontSize = 50.sp,
        color = Color.White
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = WeatherType.fromWMO(currentWeatherData.weatherType).weatherDesc,
        fontSize = 20.sp,
        color = Color.White
    )
    Spacer(modifier = Modifier.height(32.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        WeatherDataDisplay(
            value = currentWeatherData.pressure.roundToInt(),
            unit = "hpa",
            icon = ImageVector.vectorResource(id = R.drawable.ic_pressure),
            iconTint = Color.White,
            textStyle = TextStyle(color = Color.White)
        )
        WeatherDataDisplay(
            value = currentWeatherData.humidity.roundToInt(),
            unit = "%",
            icon = ImageVector.vectorResource(id = R.drawable.ic_drop),
            iconTint = Color.White,
            textStyle = TextStyle(color = Color.White)
        )
        WeatherDataDisplay(
            value = currentWeatherData.windSpeed.roundToInt(),
            unit = "km/h",
            icon = ImageVector.vectorResource(id = R.drawable.ic_wind),
            iconTint = Color.White,
            textStyle = TextStyle(color = Color.White)
        )
    }
    //        CustomText(value = currentWeather.location?.name,36, FontWeight.ExtraBold, onPrimary)
//        Spacer(modifier = Modifier.height(4.dp))
//        CustomText(value = ("${currentWeather.current?.temp_c}c"),44, FontWeight.Light, onPrimary)
//        Spacer(modifier = Modifier.height(4.dp))
//        CustomText(value = currentWeather.current?.condition?.text,24, FontWeight.Medium, onPrimaryContainer)
//        Spacer(modifier = Modifier.height(4.dp))
//        SimpleText(value = ("feels like ${currentWeather.current?.feelslike_f?.toCelcius()} C"))
}

@Composable
fun WeatherDataDisplay(
    value: Int,
    unit: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(),
    iconTint: Color = Color.White
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
