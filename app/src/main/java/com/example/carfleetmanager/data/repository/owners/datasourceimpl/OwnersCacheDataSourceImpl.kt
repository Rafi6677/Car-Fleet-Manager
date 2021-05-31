package com.example.carfleetmanager.data.repository.owners.datasourceimpl

import com.example.carfleetmanager.data.model.Owner
import com.example.carfleetmanager.data.repository.owners.datasource.OwnersCacheDataSource

class OwnersCacheDataSourceImpl : OwnersCacheDataSource {

    private var ownerList = ArrayList<Owner>()

    override suspend fun getOwnersFromCache(): List<Owner> {
        return ownerList
    }

    override suspend fun saveOwnersToCache(owners: List<Owner>) {
        ownerList.clear()
        ownerList.addAll(owners)
    }

}