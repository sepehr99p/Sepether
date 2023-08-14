package com.example.domain.repositories

import com.example.domain.entities.responses.CurrentServerEntity
import com.example.domain.entities.responses.ForecastServerEntity
import retrofit2.Response

interface WeatherRepository {

    suspend fun getCurrent(query : String) : Response<CurrentServerEntity>

    suspend fun getForecast(query: String, days : Int) : Response<ForecastServerEntity>

}