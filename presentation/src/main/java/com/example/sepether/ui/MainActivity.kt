package com.example.sepether.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.sepether.ui.theme.Color.LightColorScheme
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object{
        const val TAG = "MainActivity"
    }

    private val viewModel by viewModels<WeatherViewModel>()
    private lateinit var analytics: FirebaseAnalytics

    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

//    private fun askNotificationPermission() {
//        // This is only necessary for API level >= 33 (TIRAMISU)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
//                PackageManager.PERMISSION_GRANTED
//            ) {
//                // FCM SDK (and your app) can post notifications.
//            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
//                // TODO: display an educational UI explaining to the user the features that will be enabled
//                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
//                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
//                //       If the user selects "No thanks," allow the user to continue without notifications.
//            } else {
//                // Directly ask for the permission
//                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
//            }
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Obtain the FirebaseAnalytics instance.
        analytics = Firebase.analytics
        setContent {
            MaterialTheme(
                colorScheme = LightColorScheme
            ) {
                HomeScreen()
            }
        }
    }

    @Composable
    private fun HomeScreen() {
        val currentWeather by viewModel.currentWeather
        val painter = rememberImagePainter(data = currentWeather.current?.condition?.icon)

        LaunchedEffect(currentWeather) {
            currentWeather.current
        }


        Box(modifier = Modifier
            .fillMaxWidth()
            .background(LightColorScheme.primary)){
            Column(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart)) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Today",
                    color = LightColorScheme.onPrimary,
                    fontSize = 18.sp
                )
//                Box (modifier = Modifier
//                    .height(30.dp)
//                    .width(50.dp)){
//                    ImageWithCoil(url = (currentWeather.current?.condition?.icon)?.substring(2) ?: "cdn.weatherapi.com/weather/64x64/day/116.png")
//                    LoadImageFromUrl("https://lelolobi.com/wp-content/uploads/2021/11/Test-Logo-Small-Black-transparent-1-1.png")
//                    ImageWithCoil(url = "https://lelolobi.com/wp-content/uploads/2021/11/Test-Logo-Small-Black-transparent-1-1.png" )
//                }
//                Box (modifier = Modifier
//                    .height(30.dp)
//                    .width(50.dp)){
//                    ImageWithCoil(url = "cdn.weatherapi.com/weather/64x64/day/116.png" )
//                }
//                ImageWithCoil(currentWeather.current?.condition?.icon ?: "")
                SimpleText(value = " condition : ${currentWeather.current?.condition?.text}")
                SimpleText(value = " temp : ${currentWeather.current?.temp_c}")
                SimpleText(value = " feels like : ${currentWeather.current?.feelslike_c}")
                SimpleText(value = " wind : ${currentWeather.current?.wind_kph} km/h")
                Divider(
                    modifier = Modifier.height(12.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                )
                ForecastScreen()
            }
        }
    }


    @Composable
    fun LoadImageFromUrl(imageUrl: String) {
        Image(
            painter = rememberImagePainter(imageUrl),
            contentDescription = null, // Provide a description if needed
            modifier = Modifier.size(200.dp) // Adjust size as needed
        )
    }

    @Composable
    private fun ForecastScreen() {
        val forecastWeather by viewModel.forecast
        val scrollState = rememberLazyListState()
        LaunchedEffect(forecastWeather) {
            forecastWeather.forecast
        }
        LazyRow(modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .width(LocalConfiguration.current.screenWidthDp.dp)
            .background(LightColorScheme.primary), state = scrollState ) {
            forecastWeather.forecast?.forecastday?.let { list ->
                list.forEach {
                    item {
                        ForecastView(forecastday = it)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCurrentWeather()
        viewModel.getForecast()
    }


}