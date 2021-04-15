package ar.com.jdodevelopment.weather.ui.wheather.cities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import ar.com.jdodevelopment.weather.commons.Consts
import ar.com.jdodevelopment.weather.data.model.City
import ar.com.jdodevelopment.weather.databinding.CitiesDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CitiesDialog : BottomSheetDialogFragment() {


    private lateinit var cities: List<City>
    private var selectedCity: City? = null

    private lateinit var binding: CitiesDialogBinding


    private lateinit var pagingDataAdapter: CitiesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cities = requireArguments().getParcelableArrayList(Consts.BundleKeys.CITIES)!!
        selectedCity = requireArguments().getParcelable(Consts.BundleKeys.CITY)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = CitiesDialogBinding.inflate(inflater, container, false)
//        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initRecyclerView()
    }

    private fun initAdapter() {
        pagingDataAdapter = CitiesAdapter(selectedCity) { _, obj -> onItemClick(obj) }
        pagingDataAdapter.submitList(cities)
    }

    private fun onItemClick(obj: City) {
        setFragmentResult(Consts.RequestKeys.CITY, bundleOf(Consts.BundleKeys.CITY to obj))
        dismiss()
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            adapter = pagingDataAdapter
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(requireContext())
            //addItemDecoration(DividerItemDecoration(requireContext(),  DividerItemDecoration.VERTICAL))
        }
    }

}