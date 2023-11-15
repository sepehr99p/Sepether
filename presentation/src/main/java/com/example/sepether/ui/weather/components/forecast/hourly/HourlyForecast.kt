package com.example.sepether.ui.weather.components.forecast.hourly

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.sepether.ui.weather.WeatherViewModel

@Composable
fun WeatherForecast(
    viewModel: WeatherViewModel,
    modifier: Modifier = Modifier
) {
//    state.weatherInfo?.weatherDataPerDay?.get(0)?.let { data ->
//        Column(
//            modifier = modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp)
//        ) {
//            Text(
//                text = "Today",
//                fontSize = 20.sp,
//                color = Color.White
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            LazyRow(content = {
//                items(data) { weatherData ->
//                    val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(weatherData.time.toString())
//                    date?.let {
//                        if (it.time > System.currentTimeMillis()) {
//                            HourlyWeatherDisplay(
//                                weatherData = weatherData,
//                                modifier = Modifier
//                                    .height(100.dp)
//                                    .padding(horizontal = 16.dp)
//                            )
//                        }
//                    }
//                }
//            })
//        }
//    }
}