package ar.com.jdodevelopment.weather.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sys (
    val type: Long,
    val id: Long,
    val message: Double,
    val country: String,
    val sunrise: Long,
    val sunset: Long
): Parcelable