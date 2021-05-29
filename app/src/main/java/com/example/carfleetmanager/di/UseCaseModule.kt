package com.example.carfleetmanager.di

import com.example.carfleetmanager.domain.repository.CarsRepository
import com.example.carfleetmanager.domain.usecase.GetCarsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetCarsUseCase(
        carsRepository: CarsRepository
    ): GetCarsUseCase {
        return GetCarsUseCase(carsRepository)
    }

}