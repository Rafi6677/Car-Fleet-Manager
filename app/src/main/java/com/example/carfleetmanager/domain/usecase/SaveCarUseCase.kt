package com.example.carfleetmanager.domain.usecase

import com.example.carfleetmanager.data.model.Car
import com.example.carfleetmanager.domain.repository.CarsRepository

class SaveCarUseCase(private val carsRepository: CarsRepository) {

    suspend fun execute(car: Car) = carsRepository.saveCar(car)

}