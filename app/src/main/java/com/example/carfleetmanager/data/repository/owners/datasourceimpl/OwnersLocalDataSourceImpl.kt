package com.example.carfleetmanager.data.repository.owners.datasourceimpl

import com.example.carfleetmanager.data.db.dao.OwnersDAO
import com.example.carfleetmanager.data.model.Owner
import com.example.carfleetmanager.data.repository.owners.datasource.OwnersLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OwnersLocalDataSourceImpl(
    private val dao: OwnersDAO
) : OwnersLocalDataSource {

    override suspend fun getOwnersFromDB(): List<Owner> = dao.getAllOwners()

    override suspend fun getOwnerByIdFromDB(id: String): Owner = dao.getOwnerById(id)

    override suspend fun saveOwnersToDB(owners: List<Owner>) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.saveOwners(owners)
        }
    }

    override suspend fun clear() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteAllOwners()
        }
    }

}