package com.example.sepether.ui.weather


import android.graphics.Rect
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.example.domain.entities.responses.CurrentServerEntity
import com.example.domain.entities.responses.ForecastServerEntity
import com.example.sepether.R
import com.example.sepether.ui.theme.Color.LightColorScheme


@Composable
fun HomeScreen(viewModel: WeatherViewModel) {

    var isLoading by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightColorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart)
        ) {
            TodayWeather(viewModel.currentWeather.value)
            Divider(
                modifier = Modifier.height(12.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
            )
            ForecastScreen(viewModel.forecast)
        }
    }
}

@Composable
fun ForecastScreen(forecastWeather: State<ForecastServerEntity>) {
    val scrollState = rememberLazyListState()
    LaunchedEffect(forecastWeather) {
        forecastWeather.value.forecast
    }
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .width(LocalConfiguration.current.screenWidthDp.dp)
            .background(LightColorScheme.primary), state = scrollState
    ) {
        forecastWeather.value.forecast?.forecastday?.let { list ->
            list.forEach {
                item {
                    ForecastView(forecastday = it)
                }
            }
        }
    }
}

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

interface ClickListener{
    fun onClick()
}

@Composable
 fun TodayWeather(currentWeather: CurrentServerEntity) {


    LaunchedEffect(currentWeather) {
        currentWeather.current
    }
    var background = R.drawable.sunny
    currentWeather.current?.let {
        if (it.condition.text.contains("rain")) {
            background = R.drawable.rainy
        }
    }


    val bgImg = ContextCompat.getDrawable(
        LocalContext.current,
        R.drawable.bg_feed_view_gradient
    )

    Box(modifier = Modifier.fillMaxWidth()
        .height(300.dp)
        .clickable {
        }
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
                modifier = Modifier.padding(16.dp),
                text = "Today",
                color = LightColorScheme.onPrimary,
                fontSize = 18.sp
            )
            SimpleText(value = " condition : ${currentWeather.current?.condition?.text}")
            SimpleText(value = " temp : ${currentWeather.current?.temp_c}")
            SimpleText(value = " feels like : ${currentWeather.current?.feelslike_c}")
            SimpleText(value = " wind : ${currentWeather.current?.wind_kph} km/h")

        }
    }
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

@Composable
fun LoadImageFromUrl(imageUrl: String) {
    Image(
        painter = rememberImagePainter(imageUrl),
        contentDescription = null, // Provide a description if needed
        modifier = Modifier.size(200.dp) // Adjust size as needed
    )
}
