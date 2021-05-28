package com.example.carfleetmanager.data.repository

import android.util.Log
import com.example.carfleetmanager.data.model.Car
import com.example.carfleetmanager.data.model.CarList
import com.example.carfleetmanager.data.repository.datasource.CarsRemoteDataSource
import com.example.carfleetmanager.domain.repository.CarsRepository
import java.lang.Exception

class CarsRepositoryImpl(
    private val carsRemoteDataSource: CarsRemoteDataSource
) : CarsRepository {

    override suspend fun getCars(): List<Car>? {
        return getCarsFromAPI()
    }

    suspend fun getCarsFromAPI(): List<Car> {
        lateinit var carList: List<Car>

        try {
            val response = carsRemoteDataSource.getCars()
            val body = response.body()

            if (body != null) {
                carList = body
            }
        } catch (e: Exception) {
            Log.i("Cars_API_Tag", e.message.toString())
        }

        return carList
    }

}