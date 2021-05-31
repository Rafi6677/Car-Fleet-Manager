package com.example.carfleetmanager.domain.usecase

import com.example.carfleetmanager.domain.repository.OwnersRepository

class GetOwnerById(private val ownersRepository: OwnersRepository) {

    fun execute(id: String) = ownersRepository.getOwnerById(id)

}