package com.example.sepether.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.entities.Forecastday
import com.example.sepether.ui.theme.Color.LightColorScheme

@Composable
fun ForecastView(forecastday: Forecastday) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = forecastday.date,
            color = LightColorScheme.onPrimary,
            fontSize = 18.sp
        )
        Spacer(
            modifier = Modifier.height(4.dp)
        )
        SimpleText(value = " sunset : ${forecastday.astro.sunset}")
        SimpleText(value = " sunrise : ${forecastday.astro.sunrise}")
        SimpleText(value = " average temp : ${forecastday.day.avgtemp_c}")
    }
}

@Composable
fun SimpleText(value: String) {
    Text(
        modifier = Modifier.padding(16.dp),
        text = value,
        color = LightColorScheme.onPrimary
    )
}