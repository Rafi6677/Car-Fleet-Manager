package com.example.carfleetmanager.di

import com.example.carfleetmanager.data.repository.CarsRepositoryImpl
import com.example.carfleetmanager.data.repository.datasource.CarsCacheDataSource
import com.example.carfleetmanager.data.repository.datasource.CarsLocalDataSource
import com.example.carfleetmanager.data.repository.datasource.CarsRemoteDataSource
import com.example.carfleetmanager.domain.repository.CarsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideCarsRepository(
        carsRemoteDataSource: CarsRemoteDataSource,
        carsLocalDataSource: CarsLocalDataSource,
        carsCacheDataSource: CarsCacheDataSource
    ): CarsRepository {
        return CarsRepositoryImpl(
            carsRemoteDataSource,
            carsLocalDataSource,
            carsCacheDataSource
        )
    }

}