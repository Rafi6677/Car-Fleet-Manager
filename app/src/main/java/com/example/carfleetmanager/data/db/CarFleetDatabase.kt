package com.example.carfleetmanager.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.carfleetmanager.data.db.dao.CarsDAO
import com.example.carfleetmanager.data.db.dao.OwnersDAO
import com.example.carfleetmanager.data.model.Car

@Database(
    entities = [Car::class],
    version = 1,
    exportSchema = false
)
abstract class CarFleetDatabase : RoomDatabase() {

    abstract fun getCarsDAO(): CarsDAO
    abstract fun getOwnersDAO(): OwnersDAO

}