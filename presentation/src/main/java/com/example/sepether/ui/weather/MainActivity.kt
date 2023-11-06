package com.example.sepether.ui.weather

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.sepether.ui.music.MusicActivity
import com.example.sepether.ui.theme.Color.LightColorScheme
import com.example.sepether.utils.GPSHelper
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.Locale


@AndroidEntryPoint
class MainActivity : ComponentActivity(), LocationListener {

    companion object {
        const val TAG = "MainActivity"
    }

    private val viewModel by viewModels<WeatherViewModel>()
    private lateinit var analytics: FirebaseAnalytics
    private var latitude = 0.0
    private var longitude = 0.0
    private val gpsHelper by lazy { GPSHelper(this) }

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
        getLocationPermission()

    }

    private fun getLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), 102
            )
            return
        }
    }

    private fun updateLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
        } else {
            gpsHelper.getMyLocation()
        }
    }

    override fun onResume() {
        super.onResume()
        updateLocation()
        val query: String = if (gpsHelper.latitude != 0.0 && gpsHelper.longitude != 0.0) {
            "${gpsHelper.latitude.toString().substring(0,6)},${gpsHelper.longitude.toString().substring(0,6)}"
        } else {
            "Tehran"
        }
        viewModel.getCurrentWeather(query)
        viewModel.getForecast()
    }

    @Composable
    private fun HomeScreen() {
        val currentWeather by viewModel.currentWeather

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
                TodayWeather(currentWeather, object : ClickListener {
                    override fun onClick() {
                        startActivity(Intent(this@MainActivity, MusicActivity::class.java))
                    }

                })
                Divider(
                    modifier = Modifier.height(12.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                )
                ForecastScreen()
            }
        }
    }


    @Composable
    private fun ForecastScreen() {
        val forecastWeather by viewModel.forecast
        val scrollState = rememberLazyListState()
        LaunchedEffect(forecastWeather) {
            forecastWeather.forecast
        }

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .width(LocalConfiguration.current.screenWidthDp.dp)
                .background(LightColorScheme.primary), state = scrollState
        ) {
            forecastWeather.forecast?.forecastday?.let { list ->
                list.forEach {
                    item {
                        ForecastView(forecastday = it)
                    }
                }
            }
        }
    }

    override fun onLocationChanged(location: Location) {
//You had this as int. It is advised to have Lat/Loing as double.
        //You had this as int. It is advised to have Lat/Loing as double.
        latitude = location.latitude
        longitude = location.longitude

        val geoCoder = Geocoder(this, Locale.getDefault())
        val builder = java.lang.StringBuilder()
        try {
            val address = geoCoder.getFromLocation(latitude, longitude, 1)
            val maxLines = address!![0].maxAddressLineIndex
            for (i in 0 until maxLines) {
                val addressStr = address[0].getAddressLine(i)
                builder.append(addressStr)
                builder.append(" ")
            }
            val fnialAddress = builder.toString() //This is the complete address.
            Toast.makeText(this, fnialAddress, Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            Log.e(TAG, "onLocationChanged: ")
            // Handle IOException
        } catch (e: java.lang.NullPointerException) {
            Log.e(TAG, "onLocationChanged: ")
            // Handle NullPointerException
        }
    }

    override fun onPause() {
        super.onPause()
    }


}