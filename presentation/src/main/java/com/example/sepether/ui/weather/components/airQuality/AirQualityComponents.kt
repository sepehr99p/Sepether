package com.example.sepether.ui.weather.components.airQuality

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.domain.entities.AirQualityEntity
import com.example.sepether.R
import com.example.sepether.systemDesign.theme.dimen.corner_4
import com.example.sepether.systemDesign.theme.dimen.corner_8
import com.example.sepether.systemDesign.theme.dimen.padding_2
import com.example.sepether.systemDesign.theme.dimen.padding_4
import com.example.sepether.systemDesign.theme.dimen.padding_8
import com.example.sepether.utils.Constants.TIME_PATTERN
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun AirQualityComponent(modifier: Modifier = Modifier, data: AirQualityEntity? = null) {
    Column(
        modifier = modifier.padding(vertical = padding_8),
    ) {
        Text(
            modifier = Modifier.padding(vertical = padding_8),
            text = stringResource(id = R.string.hourly_air_quality),
            color = MaterialTheme.colorScheme.onPrimary
        )
        data?.let { airQuality ->
            LazyRow {
                with(airQuality.hourly) {
                    items(pm10.zip(time)) {
                        AirQualityListItemComponent(quality = it.first.toString(), hour = it.second)
                    }
                }

            }
        }
    }
}

@Composable
private fun AirQualityListItemComponent(
    modifier: Modifier = Modifier,
    quality: String,
    hour: String
) {
    Column(
        modifier = modifier
            .padding(padding_4)
            .clip(shape = RoundedCornerShape(corner_8))
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .padding(padding_4),

        ) {
        Text(
            text = hour, color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = quality, color = MaterialTheme.colorScheme.onPrimary)
            Text(
                modifier = Modifier.padding(start = padding_2),
                text = "μg/m³",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 8.sp
            )
        }
    }
}


@Preview
@Composable
private fun AirQualityListItemComponentPreview() {
    AirQualityListItemComponent(quality = "test", hour = "2024-04-11T06:00")
}


@Preview
@Composable
private fun AirQualityComponentPreview() {
    AirQualityComponent()
}