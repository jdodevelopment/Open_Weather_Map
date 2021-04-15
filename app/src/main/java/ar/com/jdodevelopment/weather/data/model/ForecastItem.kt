package ar.com.jdodevelopment.weather.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ForecastItem(
    val dt: Int,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
    val pop: Float,
    val sys: Sys,
    val dt_txt: String
) : Parcelable