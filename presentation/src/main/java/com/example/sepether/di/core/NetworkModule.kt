package com.example.sepether.di.core

import com.example.data.api.RetryInterceptor
import com.example.data.api.WeatherApi
import com.example.data.common.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideWeatherApiService(
        retrofit: Retrofit
    ): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        converterFactory: GsonConverterFactory,
        httpClient: OkHttpClient.Builder
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
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
//            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS) // Adjust as needed
            .readTimeout(30, TimeUnit.SECONDS)    // Adjust as needed
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