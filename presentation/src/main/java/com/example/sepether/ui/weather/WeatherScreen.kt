package com.example.sepether.ui.weather


import androidx.compose.runtime.Composable


//@Composable
//fun HomeScreen(viewModel: WeatherViewModel) {
//
//    var isLoading by remember {
//        mutableStateOf(false)
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(Primary)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .align(Alignment.CenterStart)
//        ) {
//            TodayWeather(viewModel.currentWeather.value)
//            Divider(
//                modifier = Modifier.height(12.dp),
//                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
//            )
//            ForecastScreen(viewModel.forecast)
//        }
//    }
//}
//
//@Composable
//fun ForecastScreen(forecastWeather: State<ForecastInfo>) {
//    val scrollState = rememberLazyListState()
//    LaunchedEffect(forecastWeather) {
//        forecastWeather.value.forecast
//    }
//    LazyRow(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(500.dp)
//            .width(LocalConfiguration.current.screenWidthDp.dp)
//            .background(Primary), state = scrollState
//    ) {
//        forecastWeather.value.forecast?.forecastday?.let { list ->
//            list.forEach {
//                item {
//                    ForecastView(forecastday = it)
//                }
//            }
//        }
//    }
//}

//@Composable
//fun ForecastView(forecastday: Forecastday) {
//    var background = R.drawable.sunny
//    if (forecastday.day.condition.text.contains("rain")) {
//        background = R.drawable.rainy
//    }
//
//    val bgImg = ContextCompat.getDrawable(
//        LocalContext.current,
//        R.drawable.bg_feed_view_gradient
//    )
//    Box(modifier = Modifier
//        .fillMaxWidth()
//        .height(500.dp)
//        .width(LocalConfiguration.current.screenWidthDp.dp)){
//        Image(modifier = Modifier
//            .fillMaxWidth()
//            .fillMaxHeight(),painter = painterResource(id = background), contentDescription = "test", contentScale = ContentScale.FillBounds)
//        Column(modifier = Modifier
//            .width(LocalConfiguration.current.screenWidthDp.dp)
//            .height(500.dp)
//            .drawBehind {
//                drawIntoCanvas {
//                    bgImg?.let { ninePatch ->
//                        ninePatch.run {
//                            bounds = Rect(0, 0, size.width.toInt(), size.height.toInt())
//                            draw(it.nativeCanvas)
//                        }
//                    }
//                }
//            }
//        ) {
//            Text(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                text = forecastday.date,
//                color = onPrimary,
//                fontSize = 18.sp
//            )
//            Spacer(
//                modifier = Modifier.height(4.dp)
//            )
//            SimpleText(value = " condition : ${forecastday.day.condition.text}")
//            SimpleText(value = " sunrise : ${forecastday.astro.sunrise}")
//            SimpleText(value = " sunset : ${forecastday.astro.sunset}")
//            SimpleText(value = " average temp : ${forecastday.day.avgtemp_c}")
//            SimpleText(value = " max temp : ${forecastday.day.maxtemp_c}")
//            SimpleText(value = " min temp : ${forecastday.day.mintemp_c}")
//            SimpleText(value = " will it rain : ${forecastday.day.daily_will_it_rain}")
//        }
//    }
//}

//
//
//interface ClickListener{
//    fun onClick()
//}
//
//@Composable
// fun TodayWeather(currentWeather: CurrentServerEntity) {
//
//
//    LaunchedEffect(currentWeather) {
//        currentWeather.current
//    }
//    var background = R.drawable.sunny
//    currentWeather.current?.let {
//        if (it.condition.text.contains("rain")) {
//            background = R.drawable.rainy
//        }
//    }
//
//
//    val bgImg = ContextCompat.getDrawable(
//        LocalContext.current,
//        R.drawable.bg_feed_view_gradient
//    )
//
//    Box(modifier = Modifier
//        .fillMaxWidth()
//        .height(300.dp)
//        .clickable {
//        }
//        .width(LocalConfiguration.current.screenWidthDp.dp)){
//        Image(modifier = Modifier
//            .fillMaxWidth()
//            .fillMaxHeight(),painter = painterResource(id = background), contentDescription = "test", contentScale = ContentScale.FillBounds)
//        Column(modifier = Modifier
//            .width(LocalConfiguration.current.screenWidthDp.dp)
//            .height(500.dp)
//            .drawBehind {
//                drawIntoCanvas {
//                    bgImg?.let { ninePatch ->
//                        ninePatch.run {
//                            bounds = Rect(0, 0, size.width.toInt(), size.height.toInt())
//                            draw(it.nativeCanvas)
//                        }
//                    }
//                }
//            }
//        ) {
//            Text(
//                modifier = Modifier.padding(16.dp),
//                text = "Today",
//                color = onPrimary,
//                fontSize = 18.sp
//            )
//            SimpleText(value = " condition : ${currentWeather.current?.condition?.text}")
//            SimpleText(value = " temp : ${currentWeather.current?.temp_c}")
//            SimpleText(value = " feels like : ${currentWeather.current?.feelslike_c}")
//            SimpleText(value = " wind : ${currentWeather.current?.wind_kph} km/h")
//
//        }
//    }
//}
//
//@Composable
//fun ImageWithCoil(url: String) {
//    // Create an ImageRequest with required options
//    Log.i("SEPI", "ImageWithCoil: url $url")
//    val imageRequest = ImageRequest.Builder(LocalContext.current)
//        .data(url)
//        .scale(Scale.FILL)
//        .precision(Precision.INEXACT)
//        .build()
//
//    // Use rememberImagePainter with the custom ImageRequest
//    val imagePainter = rememberImagePainter(request = imageRequest)
//
//    Image(
//        painter = imagePainter,
//        contentDescription = null, // Set a proper content description
//        modifier = Modifier
//            .width(40.dp)
//            .height(40.dp)
//            .padding(16.dp)
//    )
//}
//
//@Composable
//fun LoadImageFromUrl(imageUrl: String) {
//    Image(
//        painter = rememberImagePainter(imageUrl),
//        contentDescription = null, // Provide a description if needed
//        modifier = Modifier.size(200.dp) // Adjust size as needed
//    )
//}
