package com.example.carfleetmanager.data.repository.datasource

import com.example.carfleetmanager.data.model.CarList
import retrofit2.Response

interface CarsRemoteDataSource {

    suspend fun getCars(): Response<CarList>

}