package com.example.carfleetmanager.presentation.addnewcar

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.example.carfleetmanager.R
import com.example.carfleetmanager.databinding.ActivityAddNewCarBinding
import com.example.carfleetmanager.presentation.CarFleetViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewCarActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    private lateinit var binding: ActivityAddNewCarBinding
    val viewModel by viewModels<CarFleetViewModel>()
    val LOCATION_PERMISSION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNewCarBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return
        }
        if (ContextCompat.checkSelfPermission(
            this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            binding.fragment.findNavController().navigate(
                R.id.action_addNewCarFragment_to_selectCarLocationFragment
            )
        } else {
            onBackPressed()
        }
    }

}