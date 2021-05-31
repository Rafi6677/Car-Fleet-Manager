package com.example.carfleetmanager.data.repository.owners.datasource

import com.example.carfleetmanager.data.model.Owner

interface OwnersLocalDataSource {

    suspend fun getOwnersFromDB(): List<Owner>
    suspend fun getOwnerByIdFromDB(id: String): Owner
    suspend fun saveOwnersToDB(owners: List<Owner>)
    suspend fun clear()

}