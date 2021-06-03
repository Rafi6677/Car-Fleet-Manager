package com.example.carfleetmanager.presentation

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.carfleetmanager.data.model.Car
import com.example.carfleetmanager.data.model.Owner
import com.example.carfleetmanager.domain.usecase.GetCarsUseCase
import com.example.carfleetmanager.domain.usecase.GetOwnersUseCase
import com.example.carfleetmanager.domain.usecase.UpdateCarsUseCase
import com.example.carfleetmanager.domain.usecase.UpdateOwnersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarFleetViewModel @Inject constructor(
        private val app: Application,
        private val getCarsUseCase: GetCarsUseCase,
        private val updateCarsUseCase: UpdateCarsUseCase,
        private val getOwnersUseCase: GetOwnersUseCase,
        private val updateOwnersUseCase: UpdateOwnersUseCase
) : AndroidViewModel(app) {

    @Suppress("DEPRECATION")
    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null)
            return false

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }

        return false
    }

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
}