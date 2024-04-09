package com.example.data.mapper

import com.example.data.dto.airQuality.AirQualityDto
import com.example.data.dto.airQuality.HourlyAirQuality
import com.example.domain.entities.AirQualityEntity
import com.example.domain.entities.HourlyAirQualityEntity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun HourlyAirQuality.toDomainModel(): HourlyAirQualityEntity {
    val newList = arrayListOf<String>()
    this.time.forEach {
        val time = LocalDateTime.parse(it, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))
        newList.add("${time.hour}:${time.minute}")
    }
    return HourlyAirQualityEntity(
        time = newList,
        pm10 = this.pm10
    )

}

fun AirQualityDto.toDomainModel(): AirQualityEntity =
    AirQualityEntity(
        elevation = this.elevation,
        timezoneAbbreviation = this.timezoneAbbreviation,
        hourly = this.hourly.toDomainModel()
    )

val airQualityMapperImpl = object : MapperCallback<AirQualityDto, AirQualityEntity> {
    override fun map(value: AirQualityDto): AirQualityEntity {
        return value.toDomainModel()
    }
}