package com.example.sepether.ui.weather

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.domain.entities.responses.CurrentServerEntity
import com.example.sepether.ui.music.MusicActivity
import com.example.sepether.ui.theme.Color.LightColorScheme
import com.example.sepether.utils.GPSHelper
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
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
        analytics = Firebase.analytics
        setContent {
            MaterialTheme(
                colorScheme = LightColorScheme
            ) {
                HomeScreen(viewModel.currentWeather)
            }
        }
        getLocationPermission()
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
    private fun HomeScreen(currentWeather: State<CurrentServerEntity>) {

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
                TodayWeather(currentWeather.value, object : ClickListener {
                    override fun onClick() {
                        startActivity(Intent(this@MainActivity, MusicActivity::class.java))
                    }

                })
                Divider(
                    modifier = Modifier.height(12.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                )
                ForecastScreen(viewModel.forecast)
            }
        }
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

    override fun onLocationChanged(location: Location) {
        latitude = location.latitude
        longitude = location.longitude
        val geoCoder = Geocoder(this, Locale.getDefault())
        val finalAddress = viewModel.getFinalAddress(geoCoder,latitude,longitude)
    }

}