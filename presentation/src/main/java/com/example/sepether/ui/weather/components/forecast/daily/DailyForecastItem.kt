package com.example.sepether.ui.weather.components.forecast.daily

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.sepether.designSystem.theme.Primary_Blue
import com.example.sepether.designSystem.theme.Red_Negative
import com.example.sepether.designSystem.theme.Shapes
import com.example.sepether.designSystem.theme.dimen.image_39
import com.example.sepether.designSystem.theme.dimen.padding_16
import com.example.sepether.designSystem.theme.dimen.padding_4
import com.example.sepether.designSystem.theme.dimen.padding_8
import com.example.sepether.ui.weather.components.CustomText
import com.example.sepether.utils.extensions.airQualityBackground

@Composable
fun DailyForecastItem(
    state: DailyForecastState
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding_8)
            .clip(shape = Shapes.large)
            .airQualityBackground(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomText(value = state.time, 16, FontWeight.Medium, MaterialTheme.colorScheme.onPrimary)
        Spacer(modifier = Modifier.height(padding_4))
        Image(
            painter = painterResource(id = state.iconRes),
            contentDescription = null,
            modifier = Modifier
                .width(image_39)
                .height(image_39)
        )
        Spacer(modifier = Modifier.height(padding_4))
        Temperature(state.maxTemp, Red_Negative)
        Temperature(state.minTemp, Primary_Blue)
        Spacer(modifier = Modifier.height(padding_4))
    }

}

@Composable
fun Temperature(temp: Double, color: Color) {
    Text(
        text = "$temp°C",
        color = color,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding_16)
    )
}



