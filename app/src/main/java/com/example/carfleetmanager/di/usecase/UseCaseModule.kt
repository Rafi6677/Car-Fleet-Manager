package com.example.carfleetmanager.di.usecase

import com.example.carfleetmanager.domain.repository.CarsRepository
import com.example.carfleetmanager.domain.repository.OwnersRepository
import com.example.carfleetmanager.domain.usecase.*
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

    @Singleton
    @Provides
    fun provideGetOwnerByIdUseCase(
        ownersRepository: OwnersRepository
    ): GetOwnerById {
        return GetOwnerById(ownersRepository)
    }

    @Singleton
    @Provides
    fun provideGetOwnersUseCase(
        ownersRepository: OwnersRepository
    ): GetOwnersUseCase {
        return GetOwnersUseCase(ownersRepository)
    }

    @Singleton
    @Provides
    fun provideUpdateCarsUseCase(
        carsRepository: CarsRepository
    ): UpdateCarsUseCase {
        return UpdateCarsUseCase(carsRepository)
    }

    @Singleton
    @Provides
    fun provideUpdateOwnersUseCase(
        ownersRepository: OwnersRepository
    ): UpdateOwnersUseCase {
        return UpdateOwnersUseCase(ownersRepository)
    }

}