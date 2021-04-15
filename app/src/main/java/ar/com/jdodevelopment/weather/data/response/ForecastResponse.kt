package ar.com.jdodevelopment.weather.data.response

import android.os.Parcelable
import ar.com.jdodevelopment.weather.data.model.ForecastItem
import ar.com.jdodevelopment.weather.data.model.*
import kotlinx.parcelize.Parcelize


@Parcelize
data class ForecastResponse(
    val cod: Int,
    val message: Int,
    val cnt: Int,
    val list: List<ForecastItem>,
    val city: City
) : Parcelable