package ar.com.jdodevelopment.weather.data.response

import android.os.Parcelable
import ar.com.jdodevelopment.weather.data.model.*
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherResponse(
    val dt: Long,
    val main: Main,
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val visibility: Long,
    val wind: Wind,
    val clouds: Clouds,
    val sys: Sys,
    val timezone: Long,
    val id: Long,
    val name: String,
    val cod: Long
) : Parcelable

