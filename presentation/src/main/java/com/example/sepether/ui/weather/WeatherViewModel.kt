package com.example.sepether.ui.weather

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.Resource
import com.example.domain.entities.ForecastInfo
import com.example.domain.entities.WeatherInfo
import com.example.domain.usecases.CurrentWeatherUseCase
import com.example.domain.usecases.ForecastWeatherUseCase
import com.example.sepether.data.DataState
import com.example.sepether.utils.GPSHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val forecastWeatherUseCase: ForecastWeatherUseCase,
    private val currentWeatherUseCase: CurrentWeatherUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "WeatherViewModel"
    }

    lateinit var gpsHelper: GPSHelper
    private val scope = CoroutineScope(Job() + viewModelScope.coroutineContext)

    private val _currentWeather = mutableStateOf<DataState<WeatherInfo?>>(
        DataState(
            WeatherInfo(
                mapOf(), null
            ),
            false
        )
    )
    val currentWeather: State<DataState<WeatherInfo?>> = _currentWeather

    private val _forecast = mutableStateOf<DataState<ForecastInfo?>>(
        DataState(
            ForecastInfo(
                arrayListOf(), arrayListOf(), arrayListOf(),
                arrayListOf(), arrayListOf(), arrayListOf()
            ),
            false
        )
    )
    val forecast: State<DataState<ForecastInfo?>> = _forecast


    fun getCurrentWeather() {

        scope.launch {
            currentWeatherUseCase.invoke(currentLatitude(), currentLongitude())
                .catch {
                    _currentWeather.value = DataState(null, false)
                    Log.i(TAG, "getCurrentWeather: exception ${it.localizedMessage}")
                }.collect {
                    when (it) {
                        is Resource.Success -> {
                            _currentWeather.value = DataState(it.data, false)
                        }

                        is Resource.Loading -> {
                            _currentWeather.value = DataState(null, true)
                        }

                        is Resource.Error -> {
                            _currentWeather.value = DataState(null, false)
                            Log.i(TAG, "getCurrentWeather: error ${it.message}")
                        }
                    }
                }
        }
    }

    fun getForecast() {
        scope.launch {
            forecastWeatherUseCase.invoke(currentLatitude(), currentLongitude())
                .catch {
                    _forecast.value = DataState(null, false)
                    Log.i(TAG, "getForecast: ${it.localizedMessage}")
                }
                .collect {
                    when (it) {
                        is Resource.Success -> {
                            _forecast.value = DataState(it.data, false)
                        }

                        is Resource.Loading -> {
                            _forecast.value = DataState(null, true)
                        }

                        is Resource.Error -> {
                            _forecast.value = DataState(null, false)
                            Log.i(TAG, "getForecast: error ${it.message}")
                        }
                    }
                }
        }
    }

    fun currentLongitude(): Double {
        return gpsHelper.longitude.toString().substring(
            0,
            gpsHelper.longitude.toString().length.coerceAtMost(6)
        ).toDouble()
    }

    fun currentLatitude(): Double {
        return gpsHelper.latitude.toString().substring(
            0,
            gpsHelper.latitude.toString().length.coerceAtMost(6)
        ).toDouble()
    }


}