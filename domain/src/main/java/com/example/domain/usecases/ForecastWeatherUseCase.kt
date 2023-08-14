package com.example.domain.usecases

import com.example.domain.common.DefaultRetryPolicy
import com.example.domain.common.Resource
import com.example.domain.common.checkError
import com.example.domain.common.checkResponse
import com.example.domain.common.retryWithPolicy
import com.example.domain.entities.responses.ForecastServerEntity
import com.example.domain.repositories.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class ForecastWeatherUseCase constructor(
    private val weatherRepository: WeatherRepository
) {

    suspend operator fun invoke(query: String, days: Int): Flow<Resource<ForecastServerEntity>> =
        flow {
            emit(checkResponse(weatherRepository.getForecast(query, days)))
        }.retryWithPolicy(DefaultRetryPolicy())
            .catch { emit(checkError(it)) }
            .onStart { emit(Resource.Loading()) }

}