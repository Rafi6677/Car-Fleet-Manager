package com.example.carfleetmanager.di.repository

import com.example.carfleetmanager.data.api.CarFleetAPIService
import com.example.carfleetmanager.data.repository.cars.datasource.CarsRemoteDataSource
import com.example.carfleetmanager.data.repository.cars.datasourceimpl.CarsRemoteDataSourceImpl
import com.example.carfleetmanager.data.repository.owners.datasource.OwnersRemoteDataSource
import com.example.carfleetmanager.data.repository.owners.datasourceimpl.OwnersRemoteDataSourceImpl
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

    @Singleton
    @Provides
    fun provideOwnersRemoteDataSource(
        carFleetAPIService: CarFleetAPIService
    ): OwnersRemoteDataSource {
        return OwnersRemoteDataSourceImpl(carFleetAPIService)
    }

}