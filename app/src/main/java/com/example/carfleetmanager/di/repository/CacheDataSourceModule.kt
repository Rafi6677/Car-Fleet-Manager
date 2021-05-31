package com.example.carfleetmanager.di.repository

import com.example.carfleetmanager.data.repository.cars.datasource.CarsCacheDataSource
import com.example.carfleetmanager.data.repository.cars.datasourceimpl.CarsCacheDataSourceImpl
import com.example.carfleetmanager.data.repository.owners.datasource.OwnersCacheDataSource
import com.example.carfleetmanager.data.repository.owners.datasourceimpl.OwnersCacheDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CacheDataSourceModule {

    @Singleton
    @Provides
    fun provideCarsCacheDataSource(): CarsCacheDataSource {
        return CarsCacheDataSourceImpl()
    }

    @Singleton
    @Provides
    fun provideOwnersCacheDataSource(): OwnersCacheDataSource {
        return OwnersCacheDataSourceImpl()
    }

}