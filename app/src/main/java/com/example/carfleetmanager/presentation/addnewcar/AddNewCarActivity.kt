package com.example.carfleetmanager.presentation.addnewcar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.example.carfleetmanager.databinding.ActivityAddNewCarBinding
import com.example.carfleetmanager.presentation.CarFleetViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewCarActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    private lateinit var binding: ActivityAddNewCarBinding
    val carFleetViewModel by viewModels<CarFleetViewModel>()
    val addCarViewModel by viewModels<AddCarViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNewCarBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}