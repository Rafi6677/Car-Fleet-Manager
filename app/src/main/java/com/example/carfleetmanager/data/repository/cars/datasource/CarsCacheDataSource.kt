package com.example.carfleetmanager.data.repository.cars.datasource

import com.example.carfleetmanager.data.model.Car

interface CarsCacheDataSource {

    suspend fun getCarsFromCache(): List<Car>
    suspend fun saveCarsToCache(cars: List<Car>)

}