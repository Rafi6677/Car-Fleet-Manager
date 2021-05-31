package com.example.carfleetmanager.domain.repository

import com.example.carfleetmanager.data.model.Car
import com.example.carfleetmanager.data.model.Owner
import kotlinx.coroutines.flow.Flow

interface OwnersRepository {

    suspend fun getOwners(): List<Owner>?
    suspend fun updateOwners(): List<Owner>?
    suspend fun getOwnerById(id: String): Owner

}