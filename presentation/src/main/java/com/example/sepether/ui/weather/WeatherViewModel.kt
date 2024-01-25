package com.example.sepether.ui.weather

import android.util.Log
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
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val forecastWeatherUseCase: ForecastWeatherUseCase,
    private val currentWeatherUseCase: CurrentWeatherUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "WeatherViewModel"
    }

    lateinit var gpsHelper: GPSHelper
    private val scope = CoroutineScope(Job() + viewModelScope.coroutineContext + SupervisorJob())

    private val _currentWeather = MutableStateFlow<DataState<WeatherInfo?>>(
        DataState.LoadingState(null)
    )
    val currentWeather: StateFlow<DataState<WeatherInfo?>> = _currentWeather

    private val _forecast = MutableStateFlow<DataState<ForecastInfo?>>(
        DataState.LoadingState(null)
    )
    val forecast: StateFlow<DataState<ForecastInfo?>> = _forecast


    fun getCurrentWeather() {

        val fetchWeatherJob = scope.launch(CoroutineName("fetchCurrentWeather"),start = CoroutineStart.LAZY) {
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