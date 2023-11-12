package com.example.sepether.ui.weather

import android.location.Geocoder
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.Resource
import com.example.domain.entities.responses.CurrentServerEntity
import com.example.domain.entities.responses.ForecastServerEntity
import com.example.domain.usecases.CurrentWeatherUseCase
import com.example.domain.usecases.ForecastWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val forecastWeatherUseCase: ForecastWeatherUseCase,
    private val currentWeatherUseCase: CurrentWeatherUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "WeatherViewModel"
    }

    val scope = CoroutineScope(Job() + viewModelScope.coroutineContext)
    val rainyBackgroundUrl = "https://e0.pxfuel.com/wallpapers/619/737/desktop-wallpaper-rain-rain-weather-beautiful-background-raining-aesthetic.jpg"


    private val _currentWeather = mutableStateOf(CurrentServerEntity(null,null))
    val currentWeather: State<CurrentServerEntity> = _currentWeather

    private val _forecast = mutableStateOf(ForecastServerEntity(null,null,null))
    val forecast: State<ForecastServerEntity> = _forecast


    fun getCurrentWeather(query: String) {
        Log.i("SEPI", "getCurrentWeather: $query")
        scope.launch {
            currentWeatherUseCase.invoke(query)
                .catch {
                    Log.i(TAG, "getCurrentWeather: exception ${it.localizedMessage}")
                }.collect {
                    when (it) {
                        is Resource.Success -> {
                            Log.i(TAG, "getCurrentWeather: success")
                            _currentWeather.value = it.data!!
                        }

                        is Resource.Loading -> {
                            Log.i(TAG, "getCurrentWeather: loading")
                        }

                        is Resource.Error -> {
                            Log.i(TAG, "getCurrentWeather: error ${it.message}")
                            //todo : find a better solution for this problem later
                        }
                    }
                }
        }
    }

    fun getForecast() {
        scope.launch {
            forecastWeatherUseCase.invoke("Tehran", 3)
                .catch {
                    Log.i(TAG, "getForecast: ${it.localizedMessage}")
                }
                .collect {
                    when (it) {
                        is Resource.Success -> {
                            _forecast.value = it.data!!
                        }

                        is Resource.Loading -> {
                            Log.i(TAG, "getForecast: loading")
                        }

                        is Resource.Error -> {
                            Log.i(TAG, "getForecast: error ${it.message}")
                        }
                    }
                }
        }
    }

    fun getFinalAddress(geoCoder: Geocoder, latitude: Double, longitude: Double): String {
        val builder = java.lang.StringBuilder()
        var finalAddress = ""
        try {
            val address = geoCoder.getFromLocation(latitude, longitude, 1)
            val maxLines = address!![0].maxAddressLineIndex
            for (i in 0 until maxLines) {
                val addressStr = address[0].getAddressLine(i)
                builder.append(addressStr)
                builder.append(" ")
            }
            finalAddress = builder.toString()
        } catch (e: IOException) {
            Log.e(TAG, "IOException: ")
        } catch (e: java.lang.NullPointerException) {
            Log.e(TAG, "NullPointerException: ")
        }
        return finalAddress
    }

}