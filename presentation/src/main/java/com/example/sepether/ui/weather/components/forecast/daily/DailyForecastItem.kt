package com.example.sepether.ui.weather.components.forecast.daily

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sepether.systemDesign.theme.Primary_Blue
import com.example.sepether.systemDesign.theme.Red_Negative
import com.example.sepether.systemDesign.theme.Shapes
import com.example.sepether.systemDesign.theme.primaryContainer
import com.example.sepether.ui.weather.components.CustomText

@Composable
fun DailyForecastItem(
    state: DailyForecastState
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(shape = Shapes.large)
            .background(brush = Brush.horizontalGradient(
                colors = listOf(MaterialTheme.colorScheme.primary,MaterialTheme.colorScheme.primaryContainer)
            )),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomText(value = state.time, 16, FontWeight.Medium, MaterialTheme.colorScheme.onPrimary)
        Spacer(modifier = Modifier.height(4.dp))
        Image(
            painter = painterResource(id = state.iconRes),
            contentDescription = null,
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Temperature(state.maxTemp, Red_Negative)
        Temperature(state.minTemp, Primary_Blue)
        Spacer(modifier = Modifier.height(4.dp))
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
            .padding(4.dp)
    )
}



