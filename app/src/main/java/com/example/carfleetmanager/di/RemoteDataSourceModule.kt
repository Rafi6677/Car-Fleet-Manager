package com.example.carfleetmanager.di

import com.example.carfleetmanager.data.api.CarFleetAPIService
import com.example.carfleetmanager.data.repository.datasource.CarsRemoteDataSource
import com.example.carfleetmanager.data.repository.datasourceimpl.CarsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataSourceModule {

    @Singleton
    @Provides
    fun provideCarsRemoteDataSource(
        carFleetAPIService: CarFleetAPIService
    ): CarsRemoteDataSource {
        return CarsRemoteDataSourceImpl(carFleetAPIService)
    }

}