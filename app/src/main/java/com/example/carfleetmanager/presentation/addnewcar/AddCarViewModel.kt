package com.example.carfleetmanager.presentation.addnewcar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carfleetmanager.data.model.Car
import com.example.carfleetmanager.domain.usecase.SaveCarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

    fun saveNewCar() {
        val car = Car(
            "0",
                brand,
                model,
                registrationNumber,
                productionDate,
                color,
                latitude,
                longitude,
                mock = true,
                ownerId,
                recent = true,
                recentChanged = true
        )

        viewModelScope.launch {
            saveCarUseCase.execute(car)
        }
    }

}