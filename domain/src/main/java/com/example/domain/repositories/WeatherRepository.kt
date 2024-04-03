package com.example.domain.repositories

import com.example.domain.common.Resource
import com.example.domain.entities.AirQualityEntity
import com.example.domain.entities.ForecastInfo
import com.example.domain.entities.WeatherInfo

interface WeatherRepository {

    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
    suspend fun getForecast(lat: Double, long: Double): Resource<ForecastInfo>

    suspend fun fetchAirQuality(lat: Double, long: Double) : Resource<AirQualityEntity>

}