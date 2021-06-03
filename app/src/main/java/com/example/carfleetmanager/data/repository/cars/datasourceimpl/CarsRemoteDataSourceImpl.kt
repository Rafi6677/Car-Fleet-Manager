package com.example.carfleetmanager.data.repository.cars.datasourceimpl

import com.example.carfleetmanager.BuildConfig
import com.example.carfleetmanager.data.api.CarFleetAPIService
import com.example.carfleetmanager.data.model.Car
import com.example.carfleetmanager.data.model.CarList
import com.example.carfleetmanager.data.repository.cars.datasource.CarsRemoteDataSource
import retrofit2.Response

class CarsRemoteDataSourceImpl(
    private val carFleetAPIService: CarFleetAPIService,
) : CarsRemoteDataSource {

    override suspend fun getCars(): Response<CarList> = carFleetAPIService.getCarList()
    override suspend fun saveCar(car: Car) = carFleetAPIService.saveCar(BuildConfig.API_KEY, car)

}