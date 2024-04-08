package com.example.sepether.di.core

import com.example.data.remote.AirQualityApi
import com.example.data.remote.WeatherApi
import com.example.data.repository.remote.AirQualityRepositoryImpl
import com.example.data.repository.remote.WeatherRepositoryImpl
import com.example.domain.repositories.AirQualityRepository
import com.example.domain.repositories.WeatherRepository
import com.example.domain.usecases.AirQualityUseCase
import com.example.domain.usecases.CurrentWeatherUseCase
import com.example.domain.usecases.ForecastWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherApi: WeatherApi): WeatherRepository =
        WeatherRepositoryImpl(weatherApi)

    @Provides
    @Singleton
    fun provideAirQualityRepository(airQualityApi: AirQualityApi) : AirQualityRepository =
        AirQualityRepositoryImpl(airQualityApi)

    @Provides
    fun provideCurrentWeatherUseCase(weatherRepository: WeatherRepository): CurrentWeatherUseCase =
        CurrentWeatherUseCase(weatherRepository)

    @Provides
    fun provideForecastWeatherUseCase(weatherRepository: WeatherRepository): ForecastWeatherUseCase =
        ForecastWeatherUseCase(weatherRepository)

    @Provides
    fun provideAirQualityUseCase(airQualityRepository: AirQualityRepository): AirQualityUseCase =
        AirQualityUseCase(airQualityRepository)
}