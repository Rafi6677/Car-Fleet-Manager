package com.example.carfleetmanager.data.repository.datasourceimpl

import com.example.carfleetmanager.data.api.CarFleetAPIService
import com.example.carfleetmanager.data.model.CarList
import com.example.carfleetmanager.data.repository.datasource.CarsRemoteDataSource
import retrofit2.Response

class CarsRemoteDataSourceImpl(
    private val carFleetAPIService: CarFleetAPIService,
) : CarsRemoteDataSource {

    override suspend fun getCars(): Response<CarList> = carFleetAPIService.getCars()

}