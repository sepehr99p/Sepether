package com.example.data.remote

import com.example.data.dto.airQuality.AirQualityDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AirQualityApi {

    @GET("v1/air-quality")
    suspend fun fetchAirQuality(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double,
        @Query("hourly") hourly : String
    ) : Response<AirQualityDto>

}