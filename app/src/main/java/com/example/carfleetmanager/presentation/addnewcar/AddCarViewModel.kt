package com.example.carfleetmanager.presentation.addnewcar

import androidx.lifecycle.ViewModel
import com.example.carfleetmanager.domain.usecase.SaveCarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddCarViewModel @Inject constructor(
    private val saveCarUseCase: SaveCarUseCase
) : ViewModel() {

    var registrationNumber: String = ""
    var brand: String = ""
    var model: String = ""
    var productionDate: String = "__ / __ / ____"
    var color: String = "#ffffff"
    var ownerName: String = "-"
    var ownerId: String = ""
    var latitude: String = "-"
    var longitude: String = "-"



}