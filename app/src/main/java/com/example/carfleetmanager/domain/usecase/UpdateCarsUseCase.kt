package com.example.carfleetmanager.domain.usecase

import com.example.carfleetmanager.domain.repository.CarsRepository

class UpdateCarsUseCase(private val carsRepository: CarsRepository) {

    suspend fun execute() = carsRepository.updateCars()

}