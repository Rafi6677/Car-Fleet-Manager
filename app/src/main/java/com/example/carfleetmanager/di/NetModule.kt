package com.example.carfleetmanager.di

import com.example.carfleetmanager.BuildConfig
import com.example.carfleetmanager.data.api.CarFleetAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_API_URL)
                .build()
    }

    @Singleton
    @Provides
    fun provideCarFleetAPIService(retrofit: Retrofit): CarFleetAPIService {
        return retrofit.create(CarFleetAPIService::class.java)
    }

}