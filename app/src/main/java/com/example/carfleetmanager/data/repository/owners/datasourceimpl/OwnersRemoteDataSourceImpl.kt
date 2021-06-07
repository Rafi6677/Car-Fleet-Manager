package com.example.carfleetmanager.data.repository.owners.datasourceimpl

import com.example.carfleetmanager.data.api.CarFleetAPIService
import com.example.carfleetmanager.data.model.OwnerList
import com.example.carfleetmanager.data.repository.owners.datasource.OwnersRemoteDataSource
import retrofit2.Response

class OwnersRemoteDataSourceImpl(
    private val carFleetAPIService: CarFleetAPIService,
) : OwnersRemoteDataSource {

    override suspend fun getOwners(): Response<OwnerList> = carFleetAPIService.getOwnerList()

}