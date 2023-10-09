package com.example.sepether.ui.music

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sepether.ui.SimpleText
import com.example.sepether.ui.theme.Color

@Composable
fun MusicScreen() {
    Box(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()
        .background(Color.LightColorScheme.primary)

    ) {
        SimpleText(value = "SEPI")
        Divider(
            modifier = Modifier.height(12.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
        )
    }
}