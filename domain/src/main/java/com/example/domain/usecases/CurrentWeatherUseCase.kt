package com.example.domain.usecases

import com.example.domain.common.DefaultRetryPolicy
import com.example.domain.common.Resource
import com.example.domain.common.checkError
import com.example.domain.common.retryWithPolicy
import com.example.domain.entities.WeatherInfo
import com.example.domain.repositories.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class CurrentWeatherUseCase constructor(
    private val weatherRepository: WeatherRepository
) {

    suspend operator fun invoke(lat : Double, long : Double): Flow<Resource<WeatherInfo>> = flow {
        emit(weatherRepository.getWeatherData(lat,long))
    }.retryWithPolicy(DefaultRetryPolicy())
        .catch { emit(checkError(it)) }
        .onStart { emit(Resource.Loading()) }

}