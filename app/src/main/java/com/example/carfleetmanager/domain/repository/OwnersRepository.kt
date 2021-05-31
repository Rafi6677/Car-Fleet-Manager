package com.example.carfleetmanager.domain.repository

import com.example.carfleetmanager.data.model.Owner

interface OwnersRepository {

    suspend fun getOwners(): List<Owner>?
    suspend fun updateOwners(): List<Owner>?
    suspend fun getOwnerById(id: String): Owner

}