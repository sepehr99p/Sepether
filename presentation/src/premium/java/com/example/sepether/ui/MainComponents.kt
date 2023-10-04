package com.example.sepether.ui


import android.graphics.Rect
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Precision
import coil.size.Scale
import com.example.domain.entities.Forecastday
import com.example.sepether.R
import com.example.sepether.ui.theme.Color.LightColorScheme


@Composable
fun ForecastView(forecastday: Forecastday) {
    var background = R.drawable.sunny
    if (forecastday.day.condition.text.contains("rain")) {
        background = R.drawable.rainy
    }

    val bgImg = ContextCompat.getDrawable(
        LocalContext.current,
        R.drawable.bg_feed_view_gradient
    )
    Box(modifier = Modifier.fillMaxWidth()
        .height(500.dp)
        .width(LocalConfiguration.current.screenWidthDp.dp)){
        Image(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),painter = painterResource(id = background), contentDescription = "test", contentScale = ContentScale.FillBounds)
        Column(modifier = Modifier
            .width(LocalConfiguration.current.screenWidthDp.dp)
            .height(500.dp)
            .drawBehind {
                drawIntoCanvas {
                    bgImg?.let { ninePatch ->
                        ninePatch.run {
                            bounds = Rect(0, 0, size.width.toInt(), size.height.toInt())
                            draw(it.nativeCanvas)
                        }
                    }
                }
            }
        ) {
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
            SimpleText(value = " will it rain : ${forecastday.day.daily_will_it_rain}")

        }
    }

}


@Composable
fun SimpleText(value: String) {
    Text(
        modifier = Modifier.padding(16.dp),
        text = value,
        color = LightColorScheme.onPrimary,
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
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