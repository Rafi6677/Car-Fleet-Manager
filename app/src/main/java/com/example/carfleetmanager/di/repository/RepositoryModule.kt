package com.example.carfleetmanager.di.repository

import android.app.Application
import com.example.carfleetmanager.data.repository.cars.CarsRepositoryImpl
import com.example.carfleetmanager.data.repository.cars.datasource.CarsCacheDataSource
import com.example.carfleetmanager.data.repository.cars.datasource.CarsLocalDataSource
import com.example.carfleetmanager.data.repository.cars.datasource.CarsRemoteDataSource
import com.example.carfleetmanager.data.repository.owners.OwnersRepositoryImpl
import com.example.carfleetmanager.data.repository.owners.datasource.OwnersCacheDataSource
import com.example.carfleetmanager.data.repository.owners.datasource.OwnersLocalDataSource
import com.example.carfleetmanager.data.repository.owners.datasource.OwnersRemoteDataSource
import com.example.carfleetmanager.domain.repository.CarsRepository
import com.example.carfleetmanager.domain.repository.OwnersRepository
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
            app: Application,
            carsRemoteDataSource: CarsRemoteDataSource,
            carsLocalDataSource: CarsLocalDataSource,
            carsCacheDataSource: CarsCacheDataSource
    ): CarsRepository {
        return CarsRepositoryImpl(
            app,
            carsRemoteDataSource,
            carsLocalDataSource,
            carsCacheDataSource
        )
    }

    @Singleton
    @Provides
    fun provideOwnersRepository(
        app: Application,
        ownersRemoteDataSource: OwnersRemoteDataSource,
        ownersLocalDataSource: OwnersLocalDataSource,
        ownersCacheDataSource: OwnersCacheDataSource
    ): OwnersRepository {
        return OwnersRepositoryImpl(
            app,
            ownersRemoteDataSource,
            ownersLocalDataSource,
            ownersCacheDataSource
        )
    }

}