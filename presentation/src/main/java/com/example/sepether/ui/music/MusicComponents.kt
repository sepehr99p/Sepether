package com.example.sepether.ui.music

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sepether.ui.SimpleText
import com.example.sepether.ui.music.model.MusicFile
import com.example.sepether.ui.theme.Color


@Composable
fun MusicScreen(allMusicFiles: List<MusicFile>) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.LightColorScheme.primary)
    ) {
        Divider(
            modifier = Modifier.height(12.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            SimpleText(value = "PlayList")
            LazyColumn() {
                allMusicFiles.forEach {
                    item {
                        MusicCell(it)
                    }
                }
            }

        }
    }
}

@Composable
fun MusicCell(musicFile: MusicFile) {
    Column(modifier = Modifier.fillMaxWidth()) {
        MusicText(value = musicFile.title.toString(),FontWeight.ExtraBold)
        MusicText(value = musicFile.artist.toString(),FontWeight.Light)
        Divider(
            modifier = Modifier.height(3.dp),
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.12f)
        )
    }
}


@Composable
fun MusicText(value: String, fontWeight: FontWeight) {
    Text(
        modifier = Modifier.padding(4.dp),
        text = value,
        color = Color.LightColorScheme.onPrimary,
        fontStyle = FontStyle.Italic,
        fontWeight = fontWeight,
        fontSize = 14.sp
    )
}

