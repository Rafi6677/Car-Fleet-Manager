package com.example.carfleetmanager.data.api

import com.example.carfleetmanager.BuildConfig
import com.example.carfleetmanager.data.model.Car
import com.example.carfleetmanager.data.model.CarList
import com.example.carfleetmanager.data.model.OwnerList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CarFleetAPIService {

    @GET("/rest/car-list")
    suspend fun getCarList(
        @Query("apikey")
        apiKey: String = BuildConfig.API_KEY
    ): Response<CarList>

    @GET("/rest/person-list")
    suspend fun getOwnerList(
        @Query("apikey")
        apiKey: String = BuildConfig.API_KEY
    ): Response<OwnerList>

    @POST("/rest/car-list")
    suspend fun saveCar(
        @Query("apikey")
        apiKey: String = BuildConfig.API_KEY,
        @Body
        car: Car
    )

}