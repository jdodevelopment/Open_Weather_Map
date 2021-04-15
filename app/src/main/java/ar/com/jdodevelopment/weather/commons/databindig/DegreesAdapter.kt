package ar.com.jdodevelopment.weather.commons.databindig

import android.widget.TextView
import androidx.databinding.BindingAdapter



object DegreesAdapter {



    @JvmStatic
    @BindingAdapter("degreesCelsius")
    fun degreesCelsius(textView: TextView, degrees: Double?) {
        degrees?.let {
            val formattedValue = it.toInt().toString() + "°C"
            textView.text = formattedValue
        } ?: run {
            textView.text = "- °C"
        }

    }

}