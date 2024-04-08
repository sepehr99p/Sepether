package com.example.sepether.di.core

import com.example.data.remote.WeatherApi
import com.example.data.common.Constants
import com.example.data.common.Constants.TIME_OUT
import com.example.data.remote.AirQualityApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideForecastApiService(
        @Named("forecast") retrofit: Retrofit
    ): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAirQualityApiService(
        @Named("airQuality") retrofit: Retrofit
    ): AirQualityApi {
        return retrofit.create(AirQualityApi::class.java)
    }

    @Provides
    @Singleton
    @Named("forecast")
    fun provideForecastRetrofit(
        converterFactory: GsonConverterFactory,
        httpClient: OkHttpClient.Builder
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.FORECAST_BASE_URL)
            .addConverterFactory(converterFactory)
            .client(httpClient.build())
            .build()
    }

    @Provides
    @Singleton
    @Named("airQuality")
    fun provideAirQualityRetrofit(
        converterFactory: GsonConverterFactory,
        httpClient: OkHttpClient.Builder
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.AIR_QUALITY_BASE_URL)
            .addConverterFactory(converterFactory)
            .client(httpClient.build())
            .build()
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS) // Adjust as needed
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)    // Adjust as needed
            .retryOnConnectionFailure(true)
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

}