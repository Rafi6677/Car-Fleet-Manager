package com.example.carfleetmanager.presentation.carlist

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
import com.example.carfleetmanager.data.model.CarList
import com.example.carfleetmanager.data.util.Resource
import com.example.carfleetmanager.domain.usecase.GetCarsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CarListViewModel @Inject constructor(
    private val app: Application,
    private val getCarsUseCase: GetCarsUseCase
) : AndroidViewModel(app) {

    private fun isNetworkAvailable(context: Context?): Boolean {
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
        try {
            if (isNetworkAvailable(app)) {
                val apiResult = getCarsUseCase.execute()
                carList.postValue(apiResult)
            } else {
                //no internet connection
            }
        } catch (e: Exception) {

        }
    }

}