package com.example.sepether.utils.extensions

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Brush

fun Modifier.airQualityBackground(): Modifier =
    composed {
        background(
            brush = Brush.horizontalGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.primaryContainer,
                    MaterialTheme.colorScheme.primary
                )
            )
        )
    }

fun Modifier.dailyDetailsBackground(): Modifier =
    composed {
        background(
            brush = Brush.horizontalGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.primaryContainer,
                    MaterialTheme.colorScheme.primary
                )
            )
        )
    }
