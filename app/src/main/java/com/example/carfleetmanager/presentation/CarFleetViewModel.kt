package com.example.carfleetmanager.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carfleetmanager.data.model.Car
import com.example.carfleetmanager.data.model.Owner
import com.example.carfleetmanager.data.model.SendCar
import com.example.carfleetmanager.data.model.SendCarResponse
import com.example.carfleetmanager.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarFleetViewModel @Inject constructor(
    private val getCarsUseCase: GetCarsUseCase,
    private val updateCarsUseCase: UpdateCarsUseCase,
    private val saveCarUseCase: SaveCarUseCase,
    private val getOwnersUseCase: GetOwnersUseCase,
    private val getOwnerByIdUseCase: GetOwnerByIdUseCase,
    private val updateOwnersUseCase: UpdateOwnersUseCase
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

    val carList: MutableLiveData<List<Car>> = MutableLiveData()

    fun getCarList() = viewModelScope.launch(Dispatchers.IO) {
        carList.postValue(getCarsUseCase.execute())
    }

    fun updateCarList() = viewModelScope.launch(Dispatchers.IO) {
        carList.postValue(updateCarsUseCase.execute())
    }

    val ownerList: MutableLiveData<List<Owner>> = MutableLiveData()

    fun getOwnerList() = viewModelScope.launch(Dispatchers.IO) {
        ownerList.postValue(getOwnersUseCase.execute())
    }

    fun updateOwnerList() = viewModelScope.launch(Dispatchers.IO) {
        ownerList.postValue(updateOwnersUseCase.execute())
    }

    val owner: MutableLiveData<Owner> = MutableLiveData()

    fun getOwnerById(id: String) = viewModelScope.launch(Dispatchers.IO) {
        owner.postValue(getOwnerByIdUseCase.execute(id))
    }

    val savedCarResponse: MutableLiveData<SendCarResponse> = MutableLiveData()

    fun saveNewCar() = viewModelScope.launch(Dispatchers.IO) {
        val dateStringList = productionDate.split("/")
        val productionDateValue = "${dateStringList[2]}-${dateStringList[1]}-${dateStringList[0]}T00:00:00.000Z"

        val car = SendCar(
            brand,
            model,
            registrationNumber,
            productionDateValue,
            color,
            latitude,
            longitude,
            ownerId
        )

        savedCarResponse.postValue(saveCarUseCase.execute(car))
    }
}