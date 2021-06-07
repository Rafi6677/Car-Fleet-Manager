package com.example.carfleetmanager.data.repository.cars

import android.app.Application
import android.util.Log
import com.example.carfleetmanager.data.model.Car
import com.example.carfleetmanager.data.model.SendCar
import com.example.carfleetmanager.data.model.SendCarResponse
import com.example.carfleetmanager.data.repository.cars.datasource.CarsCacheDataSource
import com.example.carfleetmanager.data.repository.cars.datasource.CarsLocalDataSource
import com.example.carfleetmanager.data.repository.cars.datasource.CarsRemoteDataSource
import com.example.carfleetmanager.domain.repository.CarsRepository
import com.example.carfleetmanager.presentation.util.ConnectionUtils
import java.lang.Exception

class CarsRepositoryImpl(
    private val app: Application,
    private val carsRemoteDataSource: CarsRemoteDataSource,
    private val carsLocalDataSource: CarsLocalDataSource,
    private val carsCacheDataSource: CarsCacheDataSource
) : CarsRepository {

    override suspend fun getCars(): List<Car>? {
        return getCarsFromCache()
    }

    override suspend fun updateCars(): List<Car>? {
        val newListOfCars = getCarsFromAPI()

        carsLocalDataSource.clear()
        carsLocalDataSource.saveCarsToDB(newListOfCars)
        carsCacheDataSource.saveCarsToCache(newListOfCars)

        return newListOfCars
    }

    private suspend fun getCarsFromAPI(): List<Car> {
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

    private suspend fun getCarsFromDB(): List<Car> {
        lateinit var carList: List<Car>

        try {
            carList = carsLocalDataSource.getCarsFromDB()
        } catch (e: Exception) {
            Log.i("Cars_DB_Tag", e.message.toString())
        }

        if (carList.isNotEmpty()) {
            return carList
        } else if (!ConnectionUtils.isNetworkAvailable(app)) {
            return ArrayList()
        } else {
            carList = getCarsFromAPI()
            carsLocalDataSource.saveCarsToDB(carList)
        }

        return carList
    }

    private suspend fun getCarsFromCache(): List<Car> {
        lateinit var carList: List<Car>

        try {
            carList = carsCacheDataSource.getCarsFromCache()
        } catch (e: Exception) {
             Log.i("Cars_Cache_Tag", e.message.toString())
        }

        if (carList.isNotEmpty()) {
            return carList
        } else {
            carList = getCarsFromDB()
            carsCacheDataSource.saveCarsToCache(carList)
        }

        return carList
    }

    override suspend fun saveCar(car: SendCar): SendCarResponse? {
        try {
            val response = carsRemoteDataSource.saveCar(car)
            val body = response.body()

            if (body != null) {
                return body
            }
        } catch (e: Exception) {
            Log.i("Cars_API_Tag", e.message.toString())
        }

        return null
    }

}