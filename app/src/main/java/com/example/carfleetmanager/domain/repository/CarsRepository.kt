package com.example.carfleetmanager.domain.repository

import com.example.carfleetmanager.data.model.Car
import com.example.carfleetmanager.data.model.SendCar
import com.example.carfleetmanager.data.model.SendCarResponse

interface CarsRepository {

    suspend fun getCars(): List<Car>?
    suspend fun updateCars(): List<Car>?
    suspend fun saveCar(car: SendCar): SendCarResponse?

}