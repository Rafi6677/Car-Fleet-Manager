package com.example.carfleetmanager.domain.usecase

import com.example.carfleetmanager.data.model.Car
import com.example.carfleetmanager.domain.repository.CarsRepository
import com.example.carfleetmanager.domain.repository.OwnersRepository

class GetOwnersUseCase(private val ownersRepository: OwnersRepository) {

    suspend fun execute() = ownersRepository.getOwners()

}