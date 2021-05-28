package com.example.carfleetmanager.domain.repository

import com.example.carfleetmanager.data.model.Car

interface CarsRepository {

    suspend fun getCars(): List<Car>?

}