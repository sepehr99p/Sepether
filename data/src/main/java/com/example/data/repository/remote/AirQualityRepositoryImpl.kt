package com.example.data.repository.remote

import com.example.data.common.checkResponse
import com.example.data.mapper.airQualityMapperImpl
import com.example.data.remote.AirQualityApi
import com.example.domain.common.Resource
import com.example.domain.entities.AirQualityEntity
import com.example.domain.repositories.AirQualityRepository
import javax.inject.Inject

class AirQualityRepositoryImpl @Inject constructor(
    private val airQualityApi: AirQualityApi
) : AirQualityRepository {

    override suspend fun fetchAirQuality(lat: Double, long: Double): Resource<AirQualityEntity> {
        return checkResponse(airQualityApi.fetchAirQuality(lat, long, "pm10"), airQualityMapperImpl)
    }

}