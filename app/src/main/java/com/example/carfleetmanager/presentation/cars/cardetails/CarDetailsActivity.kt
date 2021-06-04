package com.example.carfleetmanager.presentation.cars.cardetails

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.carfleetmanager.data.model.Car
import com.example.carfleetmanager.databinding.ActivityCarDetailsBinding
import com.example.carfleetmanager.presentation.CarFleetViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCarDetailsBinding
    lateinit var car: Car
    val carFleetViewModel by viewModels<CarFleetViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCarDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras!!
        car = bundle.getSerializable("car") as Car
    }

}