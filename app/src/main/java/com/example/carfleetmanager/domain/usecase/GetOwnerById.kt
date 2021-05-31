package com.example.carfleetmanager.domain.usecase

import com.example.carfleetmanager.domain.repository.OwnersRepository

class GetOwnerById(private val ownersRepository: OwnersRepository) {

    suspend fun execute(id: String) = ownersRepository.getOwnerById(id)

}