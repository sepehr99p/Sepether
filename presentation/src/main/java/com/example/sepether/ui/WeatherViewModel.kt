package com.example.sepether.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.Resource
import com.example.domain.usecases.CurrentWeatherUseCase
import com.example.domain.usecases.ForecastWeatherUseCase
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

    val scope = CoroutineScope(Job() + viewModelScope.coroutineContext)

    fun getCurrentWeather() {
        scope.launch {
            currentWeatherUseCase.invoke("London")
                .catch {
                    Log.i(TAG, "getCurrentWeather: exception ${it.localizedMessage}")
                }.collect {
                    when (it) {
                        is Resource.Success -> {
                            Log.i(TAG, "getCurrentWeather: success")
                        }

                        is Resource.Loading -> {
                            Log.i(TAG, "getCurrentWeather: loading")
                        }

                        is Resource.Error -> {
                            Log.i(TAG, "getCurrentWeather: error ${it.message}")
                            //todo : find a better solution for this problem later
                            it.message?.let {
                                if (it.contains("Read timed out")) {
                                    getCurrentWeather()
                                }
                            }
                        }
                    }
                }
        }
    }

    fun getForecast() {
        scope.launch {
            forecastWeatherUseCase.invoke("London", 4)
                .catch {
                    Log.i(TAG, "getForecast: ${it.localizedMessage}")
                }
                .collect {
                    when (it) {
                        is Resource.Success -> {
                            Log.i(TAG, "getForecast: success")
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

}