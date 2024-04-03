package com.example.data.repository.remote

import com.example.data.common.checkResponse
import com.example.data.mapper.airQualityMapperImpl
import com.example.data.mapper.foreCastMapperImpl
import com.example.data.mapper.weatherMapperImpl
import com.example.data.remote.WeatherApi
import com.example.domain.common.Resource
import com.example.domain.entities.AirQualityEntity
import com.example.domain.entities.ForecastInfo
import com.example.domain.entities.WeatherInfo
import com.example.domain.repositories.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
) : WeatherRepository {

    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return checkResponse(weatherApi.getWeatherData(lat, long), weatherMapperImpl)
    }

    override suspend fun getForecast(lat: Double, long: Double): Resource<ForecastInfo> {
        return checkResponse(weatherApi.getDailyForecast(lat, long), foreCastMapperImpl)
    }

    override suspend fun fetchAirQuality(lat: Double, long: Double): Resource<AirQualityEntity> {
        return checkResponse(weatherApi.fetchAirQuality(lat, long), airQualityMapperImpl)
    }

}