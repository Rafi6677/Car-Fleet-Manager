package com.example.carfleetmanager.domain.usecase

import com.example.carfleetmanager.domain.repository.OwnersRepository

class UpdateOwnersUseCase(private val ownersRepository: OwnersRepository) {

    suspend fun execute() = ownersRepository.updateOwners()

}