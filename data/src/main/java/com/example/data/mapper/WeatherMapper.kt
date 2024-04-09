package com.example.data.mapper

import com.example.data.dto.ForecastDto
import com.example.data.dto.WeatherDataDto
import com.example.data.dto.WeatherDto
import com.example.data.dto.airQuality.AirQualityDto
import com.example.data.dto.airQuality.HourlyAirQuality
import com.example.domain.entities.AirQualityEntity
import com.example.domain.entities.ForecastInfo
import com.example.domain.entities.HourlyAirQualityEntity
import com.example.domain.entities.WeatherData
import com.example.domain.entities.WeatherInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)

fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeeds[index]
        val pressure = pressures[index]
        val humidity = humidities[index]
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = weatherCode
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues {
        it.value.map { it.data }
    }
}

fun WeatherDto.toDomainModel(): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if (now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}

fun ForecastDto.toDomainModel(): ForecastInfo = ForecastInfo(
    this.forecastDataDto.time,
    this.forecastDataDto.maxTemperatures,
    this.forecastDataDto.minTemperatures,
    this.forecastDataDto.weatherCodes,
    this.forecastDataDto.rainSum,
    this.forecastDataDto.snowfallSum
)



val weatherMapperImpl = object : MapperCallback<WeatherDto, WeatherInfo> {
    override fun map(value: WeatherDto): WeatherInfo {
        return value.toDomainModel()
    }
}

val foreCastMapperImpl = object : MapperCallback<ForecastDto, ForecastInfo> {
    override fun map(value: ForecastDto): ForecastInfo {
        return value.toDomainModel()
    }

}
