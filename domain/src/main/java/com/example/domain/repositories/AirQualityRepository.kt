package com.example.domain.repositories

import com.example.domain.common.Resource
import com.example.domain.entities.AirQualityEntity

interface AirQualityRepository {

    suspend fun fetchAirQuality(lat: Double, long: Double) : Resource<AirQualityEntity>

}