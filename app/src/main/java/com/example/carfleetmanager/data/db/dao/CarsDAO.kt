package com.example.carfleetmanager.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.carfleetmanager.data.model.Car

@Dao
interface CarsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCars(cars: List<Car>)

    @Query("DELETE FROM cars")
    suspend fun deleteAllCars()

    @Query("SELECT * FROM cars")
    suspend fun getAllCars(): List<Car>

}