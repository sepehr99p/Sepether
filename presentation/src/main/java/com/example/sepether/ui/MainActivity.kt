package com.example.sepether.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Criteria
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
    private lateinit var locationManager: LocationManager
    private var provider : String? = ""

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
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        analytics = Firebase.analytics
        setContent {
            MaterialTheme(
                colorScheme = LightColorScheme
            ) {
                HomeScreen()
            }
        }

        val criteria = Criteria()
        provider = locationManager.getBestProvider(criteria, false)
        val location = if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),110)
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        } else {
        }
        provider?.let {
            locationManager.getLastKnownLocation(it)
        } ?: kotlin.run {
            locationManager.getLastKnownLocation("")
        }

        System.out.println("Provider $provider has been selected.")
//        onLocationChanged(location)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCurrentWeather()
        viewModel.getForecast()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),120)
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

//        if (provider.isNullOrEmpty()) {
//            provider = ""
//        }
//        locationManager.requestLocationUpdates(provider!!,400L,1f,this)
    }

    private fun getCurrentLocationCity() {
        val geoCoder = Geocoder(this, Locale.getDefault()) //it is Geocoder

        val builder = StringBuilder()
        try {
            val address: List<Address>? = geoCoder.getFromLocation(latitude, longitude, 1)
            val maxLines: Int = address!![0].getMaxAddressLineIndex()
            for (i in 0 until maxLines) {
                val addressStr: String = address!![0].getAddressLine(i)
                builder.append(addressStr)
                builder.append(" ")
            }
            val fnialAddress = builder.toString() //This is the complete address.
        } catch (e: IOException) {
            Log.e(TAG, "getCurrentLocationCity: ")
        } catch (e: NullPointerException) {
            Log.e(TAG, "getCurrentLocationCity: ")
        }
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
        locationManager.removeUpdates(this);
    }


}