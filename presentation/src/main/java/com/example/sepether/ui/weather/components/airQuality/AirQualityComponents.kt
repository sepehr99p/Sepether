package com.example.sepether.ui.weather.components.airQuality

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.domain.entities.AirQualityEntity
import com.example.sepether.R
import com.example.sepether.systemDesign.theme.dimen.corner_4
import com.example.sepether.systemDesign.theme.dimen.padding_4
import com.example.sepether.systemDesign.theme.dimen.padding_8

@Composable
fun AirQualityComponent(modifier: Modifier = Modifier, data: AirQualityEntity? = null) {
    Column(
        modifier = modifier.padding(vertical = padding_8),
    ) {
        Text(
            modifier = Modifier.padding(vertical = padding_8),
            text = stringResource(id = R.string.air_quality),
            color = MaterialTheme.colorScheme.onPrimary
        )
        data?.let { airQuality ->
            LazyRow {
                items(airQuality.hourly.pm10) {
                    AirQualityListItemComponent(value = it.toString())
                }
            }
        }
    }
}

@Composable
private fun AirQualityListItemComponent(modifier: Modifier = Modifier, value: String) {
    Column(
        modifier = modifier
            .padding(padding_4)
            .clip(shape = RoundedCornerShape(corner_4))
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .padding(padding_4)
    ) {
        Text(text = value, color = MaterialTheme.colorScheme.onPrimary)
    }
}


@Preview
@Composable
private fun AirQualityListItemComponentPreview() {
    AirQualityListItemComponent(value = "test")
}


@Preview
@Composable
private fun AirQualityComponentPreview() {
    AirQualityComponent()
}