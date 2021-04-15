package ar.com.jdodevelopment.weather.ui.wheather.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ar.com.jdodevelopment.weather.commons.network.Resource
import ar.com.jdodevelopment.weather.data.model.ForecastItem
import ar.com.jdodevelopment.weather.databinding.ForecastFragmentBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ForecastFragment : Fragment() {


    private val viewModel: ForecastViewModel by viewModels()
    private lateinit var binding: ForecastFragmentBinding

    private lateinit var listAdapter: ForecastAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ForecastFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initRecyclerView()
        initListeners()
        initObservers()
        viewModel.consumeData()
    }

    /**
     * Adapter
     */
    private fun initAdapter() {
        listAdapter = ForecastAdapter()
    }


    /**
     * RecyclerView
     */
    private fun initRecyclerView() {
        binding.recyclerView.apply {
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    /**
     * Listeners
     */
    private fun initListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.consumeData()
        }
    }


    /**
     * Observers
     */
    private fun initObservers() {
        viewModel.listResource.observe(viewLifecycleOwner, { handleListResource(it) })
    }


    private fun handleListResource(resource: Resource<List<ForecastItem>>) {
        when (resource) {
            is Resource.Success -> {
                listAdapter.submitList(resource.data)
                finishLoading()
            }
            is Resource.Error -> {
                finishLoading()
                binding.errorLayout.isVisible = true
                binding.errorMessage.text = resource.message.toString()
            }
            is Resource.Loading -> startLoading()
        }
    }

    private fun startLoading() {
        binding.errorLayout.isVisible = false
        binding.swipeRefreshLayout.isRefreshing = false
        binding.progressIndicator.isVisible = true
    }

    private fun finishLoading() {
        binding.progressIndicator.isVisible = false
    }

}