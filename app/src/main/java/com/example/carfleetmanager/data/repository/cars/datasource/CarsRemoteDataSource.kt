package com.example.carfleetmanager.data.repository.cars.datasource

import com.example.carfleetmanager.data.model.CarList
import com.example.carfleetmanager.data.model.SendCar
import com.example.carfleetmanager.data.model.SendCarResponse
import retrofit2.Response

interface CarsRemoteDataSource {

    suspend fun getCars(): Response<CarList>
    suspend fun saveCar(car: SendCar): Response<SendCarResponse>

}