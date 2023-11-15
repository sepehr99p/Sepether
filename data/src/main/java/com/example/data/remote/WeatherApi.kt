package com.example.data.remote

import com.example.domain.entities.responses.CurrentServerEntity
import com.example.domain.entities.responses.ForecastServerEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey : String,
        @Query("q") query : String
    ) : Response<CurrentServerEntity>

    @GET("forecast.json")
    suspend fun getForecast(
        @Query("key") apiKey : String,
        @Query("q") query : String,
        @Query("days") days : Int
    ) : Response<ForecastServerEntity>

}