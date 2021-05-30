package com.example.carfleetmanager.di

import com.example.carfleetmanager.data.repository.datasource.CarsCacheDataSource
import com.example.carfleetmanager.data.repository.datasourceimpl.CarsCacheDataSourceImpl
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

}