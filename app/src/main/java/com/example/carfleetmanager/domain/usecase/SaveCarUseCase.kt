package com.example.carfleetmanager.domain.usecase

import com.example.carfleetmanager.data.model.SendCar
import com.example.carfleetmanager.domain.repository.CarsRepository

class SaveCarUseCase(private val carsRepository: CarsRepository) {

    suspend fun execute(car: SendCar) = carsRepository.saveCar(car)

}