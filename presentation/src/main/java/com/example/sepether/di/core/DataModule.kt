package com.example.sepether.di.core

import com.example.data.remote.WeatherApi
import com.example.data.repository.remote.WeatherRepositoryImpl
import com.example.domain.repositories.WeatherRepository
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
    fun provideWeatherRepository(weatherApi: WeatherApi) : WeatherRepository = WeatherRepositoryImpl(weatherApi)

    @Provides
    fun provideCurrentWeaetherUseCase(weatherRepository: WeatherRepository) : CurrentWeatherUseCase = CurrentWeatherUseCase(weatherRepository)

    @Provides
    fun provideForecastWeatherUseCase(weatherRepository: WeatherRepository) : ForecastWeatherUseCase = ForecastWeatherUseCase(weatherRepository)

}