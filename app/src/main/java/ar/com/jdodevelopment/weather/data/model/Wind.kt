package ar.com.jdodevelopment.weather.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Wind (
    val speed: Double,
    val deg: Long
): Parcelable