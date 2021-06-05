package com.example.carfleetmanager.presentation.carlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.carfleetmanager.R
import com.example.carfleetmanager.databinding.FragmentCarListMapBinding
import com.example.carfleetmanager.presentation.CarFleetViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class CarListMapFragment : Fragment() {

    private lateinit var binding: FragmentCarListMapBinding
    private lateinit var viewModel: CarFleetViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_car_list_map, container, false)
        initMapFragment()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCarListMapBinding.bind(view)
    }

    private fun initMapFragment() {
        viewModel = (activity as CarsActivity).viewModel

        val supportMapFragment = childFragmentManager
                .findFragmentById(R.id.map_fragment) as SupportMapFragment

        viewModel.carList.observe(viewLifecycleOwner, { response ->
            supportMapFragment.getMapAsync { map ->
                map.clear()
                for (car in response) {
                    val carPosition = LatLng(car.lat.toDouble(), car.lng.toDouble())
                    val markerOptions = MarkerOptions().apply {
                        position(carPosition)
                        title("${car.brand} ${car.model}: ${car.registration}")
                    }
                    map.addMarker(markerOptions)
                }
            }
        })
    }

}