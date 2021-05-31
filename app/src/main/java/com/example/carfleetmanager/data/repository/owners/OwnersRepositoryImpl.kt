package com.example.carfleetmanager.data.repository.owners

import android.util.Log
import com.example.carfleetmanager.data.model.Owner
import com.example.carfleetmanager.data.repository.owners.datasource.OwnersCacheDataSource
import com.example.carfleetmanager.data.repository.owners.datasource.OwnersLocalDataSource
import com.example.carfleetmanager.data.repository.owners.datasource.OwnersRemoteDataSource
import com.example.carfleetmanager.domain.repository.OwnersRepository
import java.lang.Exception

class OwnersRepositoryImpl(
    private val ownersRemoteDataSource: OwnersRemoteDataSource,
    private val ownersLocalDataSource: OwnersLocalDataSource,
    private val ownersCacheDataSource: OwnersCacheDataSource
) : OwnersRepository {

    override suspend fun getOwners(): List<Owner>? {
        return getOwnersFromCache()
    }

    override suspend fun updateOwners(): List<Owner>? {
        val newListOfOwners = getOwnersFromAPI()

        ownersLocalDataSource.clear()
        ownersLocalDataSource.saveOwnersToDB(newListOfOwners)
        ownersCacheDataSource.saveOwnersToCache(newListOfOwners)

        return newListOfOwners
    }

    override suspend fun getOwnerById(id: String): Owner {
        return ownersLocalDataSource.getOwnerByIdFromDB(id)
    }

    private suspend fun getOwnersFromAPI(): List<Owner> {
        lateinit var ownerList: List<Owner>

        try {
            val response = ownersRemoteDataSource.getOwners()
            val body = response.body()

            if (body != null) {
                ownerList = body
            }
        } catch (e: Exception) {
            Log.i("Owners_API_Tag", e.message.toString())
        }

        return ownerList
    }

    private suspend fun getOwnersFromDB(): List<Owner> {
        lateinit var ownerList: List<Owner>

        try {
            ownerList = ownersLocalDataSource.getOwnersFromDB()
        } catch (e: Exception) {
            Log.i("Cars_DB_Tag", e.message.toString())
        }

        if (ownerList.isNotEmpty()) {
            return ownerList
        } else {
            ownerList = getOwnersFromAPI()
            ownersLocalDataSource.saveOwnersToDB(ownerList)
        }

        return ownerList
    }

    private suspend fun getOwnersFromCache(): List<Owner> {
        lateinit var ownerList: List<Owner>

        try {
            ownerList = ownersCacheDataSource.getOwnersFromCache()
        } catch (e: Exception) {
            Log.i("Cars_Cache_Tag", e.message.toString())
        }

        if (ownerList.isNotEmpty()) {
            return ownerList
        } else {
            ownerList = getOwnersFromDB()
            ownersCacheDataSource.saveOwnersToCache(ownerList)
        }

        return ownerList
    }

}