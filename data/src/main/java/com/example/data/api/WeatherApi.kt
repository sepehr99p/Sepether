package com.example.data.api

import retrofit2.http.GET

interface WeatherApi {

    @GET("/current.json")
    fun getCurrentWeather()

    @GET("/forecast.json")
    fun getForecast()

}