package ar.com.jdodevelopment.weather.ui.wheather.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ar.com.jdodevelopment.weather.data.model.ForecastItem
import ar.com.jdodevelopment.weather.databinding.ForecastItemBinding


class ForecastAdapter: ListAdapter<ForecastItem, ForecastAdapter.ForecastItemViewHolder>(
    ForecastItemDiffCallback()
) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ForecastItemViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding = ForecastItemBinding.inflate(layoutInflater, viewGroup, false)
        return ForecastItemViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(viewHolder: ForecastItemViewHolder, position: Int) {
        val item = getItem(position)

        viewHolder.bindTo(item)

    }


    /**
     * ViewHolder
     */
    class ForecastItemViewHolder(private val binding: ForecastItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(item: ForecastItem) {
            binding.setObject(item)
        }
    }

    /**
     * Diff Callback
     */
    class ForecastItemDiffCallback : DiffUtil.ItemCallback<ForecastItem>() {

        override fun areItemsTheSame(oldItem: ForecastItem, newItem: ForecastItem): Boolean {
            return false // TODO implement login
        }

        override fun areContentsTheSame(oldItem: ForecastItem, newItem: ForecastItem): Boolean {
            return false  // TODO implement login
        }

    }
}
