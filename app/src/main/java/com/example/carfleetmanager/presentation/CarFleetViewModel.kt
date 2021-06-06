package com.example.carfleetmanager.presentation

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carfleetmanager.data.model.Car
import com.example.carfleetmanager.data.model.Owner
import com.example.carfleetmanager.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarFleetViewModel @Inject constructor(
    private val getCarsUseCase: GetCarsUseCase,
    private val updateCarsUseCase: UpdateCarsUseCase,
    private val getOwnersUseCase: GetOwnersUseCase,
    private val getOwnerByIdUseCase: GetOwnerByIdUseCase,
    private val updateOwnersUseCase: UpdateOwnersUseCase
) : ViewModel() {

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
}