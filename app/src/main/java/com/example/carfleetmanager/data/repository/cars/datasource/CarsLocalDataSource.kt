package com.example.carfleetmanager.data.repository.cars.datasource

import com.example.carfleetmanager.data.model.Car

interface CarsLocalDataSource {

    suspend fun getCarsFromDB(): List<Car>
    suspend fun saveCarsToDB(cars: List<Car>)
    suspend fun clear()

}