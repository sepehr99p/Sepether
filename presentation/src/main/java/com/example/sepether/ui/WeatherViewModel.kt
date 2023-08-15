package com.example.sepether.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import kotlinx.coroutines.delay
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



    private val _newData = mutableStateOf(CurrentServerEntity(null,null))
    val newData: State<CurrentServerEntity> = _newData

    private val _forecast = MutableLiveData<ForecastServerEntity>()
    val forecast: LiveData<ForecastServerEntity> get() = _forecast

    fun getCurrentWeather() {
        scope.launch {
            currentWeatherUseCase.invoke("Tehran")
                .catch {
                    Log.i(TAG, "getCurrentWeather: exception ${it.localizedMessage}")
                }.collect {
                    when (it) {
                        is Resource.Success -> {
                            Log.i(TAG, "getCurrentWeather: success")
                            _newData.value = it.data!!
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
            forecastWeatherUseCase.invoke("Tehran", 4)
                .catch {
                    Log.i(TAG, "getForecast: ${it.localizedMessage}")
                }
                .collect {
                    when (it) {
                        is Resource.Success -> {
                            Log.i(TAG, "getForecast: success")
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

}