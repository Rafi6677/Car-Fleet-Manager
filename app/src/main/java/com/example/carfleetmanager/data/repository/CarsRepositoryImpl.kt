package com.example.carfleetmanager.data.repository

import android.util.Log
import com.example.carfleetmanager.data.model.Car
import com.example.carfleetmanager.data.repository.datasource.CarsCacheDataSource
import com.example.carfleetmanager.data.repository.datasource.CarsLocalDataSource
import com.example.carfleetmanager.data.repository.datasource.CarsRemoteDataSource
import com.example.carfleetmanager.domain.repository.CarsRepository
import java.lang.Exception

class CarsRepositoryImpl(
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

}