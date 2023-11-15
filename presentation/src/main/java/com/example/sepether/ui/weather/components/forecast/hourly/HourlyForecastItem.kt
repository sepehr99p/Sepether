package com.example.sepether.ui.weather.components.forecast.hourly

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.sepether.ui.weather.WeatherViewModel

@Composable
fun HourlyWeatherDisplay(
    viewModel: WeatherViewModel,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White
) {
//    viewModel.forecast.value.forecast
//
//    val formattedTime = remember(weatherData) {
//        weatherData.time.format(
//            DateTimeFormatter.ofPattern("HH:mm")
//        )
//    }
//    Column(
//        modifier = modifier,
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.SpaceBetween
//    ) {
//        Text(
//            text = formattedTime,
//            color = Color.LightGray
//        )
//        Image(
//            painter = painterResource(id = weatherData.weatherType.iconRes),
//            contentDescription = null,
//            modifier = Modifier.width(40.dp)
//        )
//        Text(
//            text = "${weatherData.temperatureCelsius}°C",
//            color = textColor,
//            fontWeight = FontWeight.Bold
//        )
//    }
}