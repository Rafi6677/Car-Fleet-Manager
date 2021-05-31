package com.example.carfleetmanager.data.repository.cars.datasourceimpl

import com.example.carfleetmanager.data.db.dao.CarsDAO
import com.example.carfleetmanager.data.model.Car
import com.example.carfleetmanager.data.repository.cars.datasource.CarsLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CarsLocalDataSourceImpl(
    private val dao: CarsDAO
) : CarsLocalDataSource {

    override suspend fun getCarsFromDB(): List<Car> = dao.getAllCars()

    override suspend fun saveCarsToDB(cars: List<Car>) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.saveCars(cars)
        }
    }

    override suspend fun clear() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteAllCars()
        }
    }

}