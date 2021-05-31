package com.example.carfleetmanager.di.repository

import com.example.carfleetmanager.data.db.dao.CarsDAO
import com.example.carfleetmanager.data.db.dao.OwnersDAO
import com.example.carfleetmanager.data.repository.cars.datasource.CarsLocalDataSource
import com.example.carfleetmanager.data.repository.cars.datasourceimpl.CarsLocalDataSourceImpl
import com.example.carfleetmanager.data.repository.owners.datasource.OwnersLocalDataSource
import com.example.carfleetmanager.data.repository.owners.datasourceimpl.OwnersLocalDataSourceImpl
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

    @Singleton
    @Provides
    fun provideOwnersLocalDataSource(dao: OwnersDAO): OwnersLocalDataSource {
        return OwnersLocalDataSourceImpl(dao)
    }

}