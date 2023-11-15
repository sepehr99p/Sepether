package com.example.sepether.ui.weather.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import com.example.sepether.ui.theme.Color.DarkColorScheme

@Composable
fun CustomText(value : String?,fontSize : Int,fontWeight: FontWeight, color : Color) {
    value?.let {
        Text(
            modifier = Modifier.padding(16.dp),
            text = it,
            color = color,
            fontStyle = FontStyle.Italic,
            fontWeight = fontWeight,
            fontSize = fontSize.sp
        )
    }
}


@Composable
fun SimpleText(value: String?) {
    value?.let {
        Text(
            modifier = Modifier.padding(16.dp),
            text = it,
            color = DarkColorScheme.onPrimary,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}