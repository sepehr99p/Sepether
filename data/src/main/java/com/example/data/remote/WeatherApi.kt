package com.example.data.remote

import com.example.data.dto.ForecastDto
import com.example.data.dto.WeatherDto
import com.example.data.dto.airQuality.AirQualityDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/forecast?hourly=temperature_2m,weathercode,relativehumidity_2m,windspeed_10m,pressure_msl")
    suspend fun getWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double
    ): Response<WeatherDto>

    @GET("v1/forecast?daily=weathercode,temperature_2m_max,temperature_2m_min,rain_sum,snowfall_sum")
    suspend fun getDailyForecast(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double
    ) : Response<ForecastDto>




}