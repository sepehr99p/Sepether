package com.example.sepether.ui.weather

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.Resource
import com.example.domain.entities.AirQualityEntity
import com.example.domain.entities.ForecastInfo
import com.example.domain.entities.WeatherInfo
import com.example.domain.usecases.AirQualityUseCase
import com.example.domain.usecases.CurrentWeatherUseCase
import com.example.domain.usecases.ForecastWeatherUseCase
import com.example.sepether.ui.DataState
import com.example.sepether.utils.GPSHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val forecastWeatherUseCase: ForecastWeatherUseCase,
    private val currentWeatherUseCase: CurrentWeatherUseCase,
    private val airQualityUseCase: AirQualityUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "WeatherViewModel"
    }

    //    A CEH is optional. It should only be used when you really need to do something with unhandled exceptions.
    private val ceh = CoroutineExceptionHandler { _, t ->
        Log.e(TAG, "ceh", t)
    }

    lateinit var gpsHelper: GPSHelper
    private val scope =
        CoroutineScope(Job() + viewModelScope.coroutineContext + SupervisorJob() + ceh)

    private val _currentWeather = MutableStateFlow<DataState<WeatherInfo?>>(
        DataState.LoadingState(null)
    )
    val currentWeather: StateFlow<DataState<WeatherInfo?>> = _currentWeather

    private val _forecast = MutableStateFlow<DataState<ForecastInfo?>>(
        DataState.LoadingState(null)
    )
    val forecast: StateFlow<DataState<ForecastInfo?>> = _forecast

    private val _airQuality = MutableStateFlow<DataState<AirQualityEntity?>>(
        DataState.LoadingState(null)
    )

    val airQuality: StateFlow<DataState<AirQualityEntity?>> = _airQuality

    init {

    }

    fun fetchAirQuality() {
        Log.i(TAG, ": ")
        scope.launch {
            airQualityUseCase.invoke(currentLatitude(),currentLongitude())
                .catch {
                    _airQuality.value = DataState.FailedState(null)
                    Log.i(TAG, "fetchAirQuality: exception ${it.localizedMessage}")
                }.collect{
                    when(it) {
                        is Resource.Error -> {
                            _airQuality.value = DataState.FailedState(null)
                        }
                        is Resource.Loading -> {
                            _airQuality.value = DataState.LoadingState(null)
                        }
                        is Resource.Success -> {
                            _airQuality.value = DataState.LoadedState(it.data)
                        }
                    }
                }
        }
    }


    fun getCurrentWeather() {
        val fetchWeatherJob =
            scope.launch(CoroutineName("fetchCurrentWeather"), start = CoroutineStart.LAZY) {
                currentWeatherUseCase.invoke(currentLatitude(), currentLongitude())
                    .catch {
                        _currentWeather.value = DataState.FailedState(null)
                        Log.i(TAG, "getCurrentWeather: exception ${it.localizedMessage}")
                    }.collect {
                        when (it) {
                            is Resource.Success -> {
                                _currentWeather.value = DataState.LoadedState(it.data)
                            }

                            is Resource.Loading -> {
                                _currentWeather.value = DataState.LoadingState(null)
                            }

                            is Resource.Error -> {
                                _currentWeather.value = DataState.FailedState(null)
                                Log.i(TAG, "getCurrentWeather: error ${it.message}")
                            }
                        }
                    }
            }

        fetchWeatherJob.start()
        if (fetchWeatherJob.isCancelled) {
            throw CancellationException()
        }
    }

    fun getForecast() {
        scope.launch(CoroutineName("FetchForecast")) {
            forecastWeatherUseCase.invoke(currentLatitude(), currentLongitude())
                .catch {
                    _forecast.value = DataState.FailedState(null)
                    Log.i(TAG, "getForecast: ${it.localizedMessage}")
                }
                .collect {
                    when (it) {
                        is Resource.Success -> {
                            _forecast.value = DataState.LoadedState(it.data)
                        }

                        is Resource.Loading -> {
                            _forecast.value = DataState.LoadingState(null)
                        }

                        is Resource.Error -> {
                            _forecast.value = DataState.FailedState(null)
                            Log.i(TAG, "getForecast: error ${it.message}")
                        }
                    }
                }
        }
    }

    private fun currentLongitude(): Double {
        return gpsHelper.longitude.toString().substring(
            0,
            gpsHelper.longitude.toString().length.coerceAtMost(6)
        ).toDouble()
    }

    private fun currentLatitude(): Double {
        return gpsHelper.latitude.toString().substring(
            0,
            gpsHelper.latitude.toString().length.coerceAtMost(6)
        ).toDouble()
    }


}