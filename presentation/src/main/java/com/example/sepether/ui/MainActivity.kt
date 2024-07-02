package com.example.sepether.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.example.sepether.designSystem.theme.SepetherTheme
import com.example.sepether.ui.weather.WeatherViewModel
import com.example.sepether.utils.GPSHelper
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        analytics = FirebaseAnalytics.getInstance(this)
        viewModel.gpsHelper = GPSHelper(this)
        setContent {
            SepetherTheme {
                SepetherNavHost(viewModel = viewModel)
            }
        }

        val bundle = Bundle()
        bundle.putString("key","testing value")

        analytics.logEvent("testing",bundle)
        analytics.logEvent("name") {
            param("key","val")
        }
        getLocationPermission()
    }

    override fun onResume() {
        super.onResume()
        updateLocation()
        viewModel.getCurrentWeather()
        viewModel.getForecast()
        viewModel.fetchAirQuality()
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
        } else {
            updateLocation()
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
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), 102
            )
            //
        } else {
            viewModel.gpsHelper.getMyLocation()
        }
    }

    override fun onLocationChanged(location: Location) {
        latitude = location.latitude
        longitude = location.longitude
        val geoCoder = Geocoder(this, Locale.getDefault())
    }


}