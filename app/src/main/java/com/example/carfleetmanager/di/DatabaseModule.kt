package com.example.carfleetmanager.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.carfleetmanager.data.db.CarFleetDatabase
import com.example.carfleetmanager.data.db.dao.CarsDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(app: Application): CarFleetDatabase {
        return Room.databaseBuilder(
            app,
            CarFleetDatabase::class.java,
            "carfleetdatabase"
        ).build()
    }

    @Singleton
    @Provides
    fun provideCarsDAO(carFleetDatabase: CarFleetDatabase): CarsDAO {
        return carFleetDatabase.getCarsDAO()
    }

}