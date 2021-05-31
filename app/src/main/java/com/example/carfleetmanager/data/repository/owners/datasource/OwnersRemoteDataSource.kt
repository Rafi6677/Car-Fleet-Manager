package com.example.carfleetmanager.data.repository.owners.datasource

import com.example.carfleetmanager.data.model.OwnerList
import retrofit2.Response

interface OwnersRemoteDataSource {

    suspend fun getOwners(): Response<OwnerList>

}