package com.example.sepether.ui.weather.components.ex

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.sepether.ui.theme.primaryContainer

internal fun Modifier.weatherScreenModifier() : Modifier = composed {
    fillMaxWidth()
    fillMaxHeight()
    verticalScroll(rememberScrollState())
    background(MaterialTheme.colorScheme.primary)
}

