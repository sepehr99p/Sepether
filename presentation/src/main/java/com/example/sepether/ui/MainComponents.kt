package com.example.sepether.ui


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Precision
import coil.size.Scale
import com.example.domain.entities.Forecastday
import com.example.sepether.ui.theme.Color.LightColorScheme



@Composable
fun ForecastView(forecastday: Forecastday) {
    Column(modifier = Modifier.width(IntrinsicSize.Max)) {
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
        SimpleText(value = " condition : ${forecastday.day.condition.text}")
        SimpleText(value = " sunrise : ${forecastday.astro.sunrise}")
        SimpleText(value = " sunset : ${forecastday.astro.sunset}")
        SimpleText(value = " average temp : ${forecastday.day.avgtemp_c}")
        SimpleText(value = " max temp : ${forecastday.day.maxtemp_c}")
        SimpleText(value = " min temp : ${forecastday.day.mintemp_c}")

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


@Composable
fun ImageWithCoil(url: String) {
    // Create an ImageRequest with required options
    Log.i("SEPI", "ImageWithCoil: url $url")
    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data(url)
        .scale(Scale.FILL)
        .precision(Precision.INEXACT)
        .build()

    // Use rememberImagePainter with the custom ImageRequest
    val imagePainter = rememberImagePainter(request = imageRequest)

    Image(
        painter = imagePainter,
        contentDescription = null, // Set a proper content description
        modifier = Modifier
            .width(40.dp)
            .height(40.dp)
            .padding(16.dp)
    )
}