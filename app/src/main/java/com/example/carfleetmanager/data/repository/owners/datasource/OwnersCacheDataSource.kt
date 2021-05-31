package com.example.carfleetmanager.data.repository.owners.datasource

import com.example.carfleetmanager.data.model.Owner

interface OwnersCacheDataSource {

    suspend fun getOwnersFromCache(): List<Owner>
    suspend fun saveOwnersToCache(owners: List<Owner>)

}