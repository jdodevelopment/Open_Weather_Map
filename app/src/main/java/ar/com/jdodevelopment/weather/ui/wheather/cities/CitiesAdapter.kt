package ar.com.jdodevelopment.weather.ui.wheather.cities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ar.com.jdodevelopment.weather.data.model.City
import ar.com.jdodevelopment.weather.databinding.ItemCityBinding


class CitiesAdapter(
    private val selection: City?,
    private val onItemClickListener: (view: View, item: City) -> Unit
) : ListAdapter<City, CitiesAdapter.SortViewHolder>(
    CityDiffCallback()
) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): SortViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemCityBinding.inflate(layoutInflater, viewGroup, false)
        return SortViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: SortViewHolder, position: Int) {
        val item = getItem(position)
        viewHolder.bindTo(item, selection, onItemClickListener)
     }

    /**
     * ViewHolder
     */
    class SortViewHolder(private val binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(item: City, selection: City?, onItemClickListener: (view: View, item: City) -> Unit) {
            binding.setObject(item)
            selection?.let {
                binding.selected = it.id == item.id
            }

            binding.root.setOnClickListener { view: View ->
                onItemClickListener(view, item)
            }
        }
    }

    /**
     * Diff Callback
     */
    class CityDiffCallback : DiffUtil.ItemCallback<City>() {

        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return newItem.name == oldItem.name
        }

    }


}



