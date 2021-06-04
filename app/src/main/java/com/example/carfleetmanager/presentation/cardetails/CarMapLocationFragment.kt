package com.example.carfleetmanager.presentation.cardetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.carfleetmanager.R
import com.example.carfleetmanager.data.model.Car
import com.example.carfleetmanager.databinding.FragmentCarMapLocationBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class CarMapLocationFragment : Fragment() {

    private lateinit var binding: FragmentCarMapLocationBinding
    private lateinit var car: Car

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_car_map_location, container, false)
        getCarData()
        initMapFragment()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCarMapLocationBinding.bind(view)

        initButtons()
    }

    private fun getCarData() {
        car = requireArguments().getSerializable("car") as Car
    }

    private fun initMapFragment() {
        val supportMapFragment = childFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment

        val carPosition = LatLng(car.lat.toDouble(), car.lng.toDouble())

        supportMapFragment.getMapAsync { map ->
            val markerOptions = MarkerOptions().apply {
                position(carPosition)
                title("${car.brand} ${car.model}: ${car.registration}")
            }
            map.apply {
                clear()
                animateCamera(CameraUpdateFactory.newLatLngZoom(carPosition, 10.toFloat()))
                addMarker(markerOptions)
            }
        }
    }

    private fun initButtons() {
        binding.backImageButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

}