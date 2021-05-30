package com.example.carfleetmanager.presentation.addnewcar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.carfleetmanager.R
import com.example.carfleetmanager.databinding.ActivityAddNewCarBinding

class AddNewCarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNewCarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNewCarBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}