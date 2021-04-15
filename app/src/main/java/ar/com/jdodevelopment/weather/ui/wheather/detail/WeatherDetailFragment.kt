package ar.com.jdodevelopment.weather.ui.wheather.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ar.com.jdodevelopment.weather.ui.wheather.cities.CitiesDialog
import ar.com.jdodevelopment.weather.R
import ar.com.jdodevelopment.weather.commons.Consts
import ar.com.jdodevelopment.weather.commons.extensions.navigateSafe
import ar.com.jdodevelopment.weather.commons.network.Resource
import ar.com.jdodevelopment.weather.data.model.City
import ar.com.jdodevelopment.weather.data.response.WeatherResponse
import ar.com.jdodevelopment.weather.databinding.WeatherDetailFragmentBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WeatherDetailFragment : Fragment() {

    private val viewModel: WeatherDetailViewModel by viewModels()
    private lateinit var binding: WeatherDetailFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WeatherDetailFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initResultListeners()
        initObservers()
        viewModel.consumeData()
    }

    /**
     * Listeners
     */
    private fun initListeners() {
        binding.button.setOnClickListener {
            val navDirections = WeatherDetailFragmentDirections.actionOpenForecast(viewModel.city.value!!)
            findNavController().navigateSafe(R.id.weatherDetailFragment, navDirections)
        }
        binding.button2.setOnClickListener {
            openCitiesDialog()
        }
    }

    private fun openCitiesDialog() {
        val dialogFragment = CitiesDialog()
        dialogFragment.arguments = buildBundle()
        dialogFragment.show(parentFragmentManager, null)
        parentFragmentManager.executePendingTransactions()
    }

    private fun buildBundle(): Bundle {
        return bundleOf(
            Consts.BundleKeys.CITIES to viewModel.cities.value,
            Consts.BundleKeys.CITY to viewModel.city.value
        )
    }

    /**
     * ResultListeners
     */
    private fun initResultListeners() {
        setFragmentResultListener(Consts.RequestKeys.CITY) { _, bundle ->
            bundle.getParcelable<City>(Consts.BundleKeys.CITY)?.let { viewModel.setCity(it) }
        }
    }

    /**
     * Observers
     */
    private fun initObservers() {
        viewModel.wheaterData.observe(viewLifecycleOwner, { handleListResource(it) })
    }

    private fun handleListResource(resource: Resource<WeatherResponse>) {
        when (resource) {
            is Resource.Success -> {
                finishLoading()
            }
            is Resource.Error -> {
                finishLoading()
                Snackbar.make(binding.root, resource.message.toString(), Snackbar.LENGTH_LONG).show()
            }
            is Resource.Loading -> startLoading()
        }
    }

    private fun startLoading() {
        binding.progressIndicator.isVisible = true
    }

    private fun finishLoading() {
        binding.progressIndicator.isVisible = false
    }

}
