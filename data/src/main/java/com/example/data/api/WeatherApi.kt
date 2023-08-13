package com.example.data.api

import com.example.data.entities.response.CurrentResponse
import com.example.data.entities.response.ForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/current.json")
    fun getCurrentWeather(
        @Query("key") apiKey : String,
        @Query("q") query : String
    ) : Response<CurrentResponse>

    @GET("/forecast.json")
    fun getForecast(
        @Query("key") apiKey : String,
        @Query("q") query : String,
        @Query("days") days : Int
    ) : Response<ForecastResponse>

}