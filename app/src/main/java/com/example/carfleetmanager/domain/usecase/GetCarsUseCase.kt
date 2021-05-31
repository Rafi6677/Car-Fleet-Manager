package com.example.carfleetmanager.domain.usecase

import com.example.carfleetmanager.domain.repository.CarsRepository

class GetCarsUseCase(private val carsRepository: CarsRepository) {

    suspend fun execute() = carsRepository.getCars()

}