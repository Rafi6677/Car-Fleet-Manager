package com.example.carfleetmanager.data.api

import com.example.carfleetmanager.BuildConfig
import com.example.carfleetmanager.data.model.CarList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CarFleetAPIService {

    @GET("/rest/person-list")
    suspend fun getCars(
        @Query("apikey")
        apiKey: String = BuildConfig.API_KEY
    ): Response<CarList>

}