package com.example.data.repository

import com.example.data.remote.WeatherApi
import com.example.data.common.Constants.API_KEY
import com.example.domain.entities.responses.CurrentServerEntity
import com.example.domain.entities.responses.ForecastServerEntity
import com.example.domain.repositories.WeatherRepository
import retrofit2.Response

class WeatherRepositoryImpl constructor(
    private val weatherApi: WeatherApi
) : WeatherRepository {

    override suspend fun getCurrent(query: String): Response<CurrentServerEntity> {
        return weatherApi.getCurrentWeather(API_KEY,query)
    }

    override suspend fun getForecast(query: String, days: Int): Response<ForecastServerEntity> {
        return weatherApi.getForecast(API_KEY,query,days)
    }

}
