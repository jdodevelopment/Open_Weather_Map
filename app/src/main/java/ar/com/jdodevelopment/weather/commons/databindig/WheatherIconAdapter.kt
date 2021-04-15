package ar.com.jdodevelopment.weather.commons.databindig

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


object WheatherIconAdapter {


    @JvmStatic
    @BindingAdapter("weatherIcon")
    fun weatherIcon(imageView: ImageView, icon: String?) {
        if (!icon.isNullOrEmpty()) {
            val iconUrl = "http://openweathermap.org/img/w/" + icon + ".png"
            Glide.with(imageView.context)
                .load(iconUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
        }

    }


}