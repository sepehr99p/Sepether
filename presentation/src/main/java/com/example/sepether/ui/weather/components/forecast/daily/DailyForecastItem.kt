package com.example.sepether.ui.weather.components.forecast.daily

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.domain.entities.Forecastday
import com.example.sepether.ui.theme.primaryContainer
import com.example.sepether.ui.weather.components.SimpleText

@Composable
fun DailyForecastItem(forecastday: Forecastday) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 2.dp)
            .clip(shape = RoundedCornerShape(8.dp, 8.dp, 8.dp, 8.dp))
            .background(
                primaryContainer
            ),
    ) {
        SimpleText(value = forecastday.day.avgtemp_c.toString())
    }
    Spacer(modifier = Modifier.height(4.dp))

}