package com.example.carfleetmanager.presentation.cars.carlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.carfleetmanager.R
import com.example.carfleetmanager.databinding.FragmentCarListMapBinding

class CarListMapFragment : Fragment() {

    private lateinit var binding: FragmentCarListMapBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_car_list_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCarListMapBinding.bind(view)
    }

}