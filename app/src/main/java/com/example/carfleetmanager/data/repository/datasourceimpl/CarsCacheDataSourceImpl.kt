package com.example.carfleetmanager.data.repository.datasourceimpl

import com.example.carfleetmanager.data.model.Car
import com.example.carfleetmanager.data.repository.datasource.CarsCacheDataSource

class CarsCacheDataSourceImpl : CarsCacheDataSource {

    private var carList = ArrayList<Car>()

    override suspend fun getCarsFromCache(): List<Car> {
        return carList
    }

    override suspend fun saveCarsToCache(cars: List<Car>) {
        carList.clear()
        carList = ArrayList(cars)
    }

}