package com.example.carfleetmanager.di

import com.example.carfleetmanager.data.db.dao.CarsDAO
import com.example.carfleetmanager.data.repository.datasource.CarsLocalDataSource
import com.example.carfleetmanager.data.repository.datasourceimpl.CarsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataSourceModule {

    @Singleton
    @Provides
    fun provideCarsLocalDataSource(dao: CarsDAO): CarsLocalDataSource {
        return CarsLocalDataSourceImpl(dao)
    }

}