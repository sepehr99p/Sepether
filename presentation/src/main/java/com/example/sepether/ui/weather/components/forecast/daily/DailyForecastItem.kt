package com.example.sepether.ui.weather.components.forecast.daily

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.domain.entities.Forecastday
import com.example.sepether.R
import com.example.sepether.ui.theme.primaryContainer
import com.example.sepether.ui.weather.components.SimpleText
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DailyForecastItem(forecastday: Forecastday) {

    LaunchedEffect(forecastday) {
        forecastday.day
        forecastday.date
    }
    var icon = R.drawable.ic_sun
    if (forecastday.day.daily_will_it_snow == 1) {
        icon = R.drawable.ic_snow
    }
    if (forecastday.day.daily_will_it_snow == 1) {
        icon = R.drawable.ic_drop
    }

    val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val date = LocalDate.parse(forecastday.date , dateFormat)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 2.dp)
            .clip(shape = RoundedCornerShape(8.dp, 8.dp, 8.dp, 8.dp))
            .background(
                primaryContainer
            ),
    ) {
        Row {
            SimpleText(value = (date.dayOfWeek.name))
        }
        Row {
            SimpleText(value = forecastday.day.condition.text)
            Spacer(modifier = Modifier.width(4.dp))
            SimpleText(value = "${forecastday.day.avgtemp_c} c ")
            Spacer(modifier = Modifier.width(4.dp))
            Image(painter = painterResource(id = icon), contentDescription = "condition", modifier = Modifier.align(Alignment.CenterVertically))
        }
    }
    Spacer(modifier = Modifier.height(4.dp))

}