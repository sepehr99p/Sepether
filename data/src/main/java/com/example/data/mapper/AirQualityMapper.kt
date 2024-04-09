package com.example.data.mapper

import com.example.data.dto.airQuality.AirQualityDto
import com.example.data.dto.airQuality.HourlyAirQuality
import com.example.domain.entities.AirQualityEntity
import com.example.domain.entities.DailyAirQualityEntity
import com.example.domain.entities.HourlyAirQualityEntity
import java.text.SimpleDateFormat
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
        hourly = this.hourly.toDomainModel(),
        daily = getDailyAirQuality(this.hourly)
    )

val airQualityMapperImpl = object : MapperCallback<AirQualityDto, AirQualityEntity> {
    override fun map(value: AirQualityDto): AirQualityEntity {
        return value.toDomainModel()
    }
}

fun getDailyAirQuality(hourly: HourlyAirQuality): DailyAirQualityEntity {
    val newList = arrayListOf<Pair<String,Float>>()
    val daily = arrayListOf<Pair<String,Double>>()

    hourly.time.zip(hourly.pm10).forEach {
        newList.add(Pair(first = it.first.dayOfWeek(), second = it.second))
    }

    newList.groupBy {
        it.first
    }.forEach {
        daily.add(Pair(it.key,it.value.map { it.second }.average()))
    }

    return DailyAirQualityEntity(
        data = daily
    )
}


fun String.dayOfWeek() : String {
    return SimpleDateFormat("EEE").format(SimpleDateFormat("yyyy-MM-dd").parse(this))
}