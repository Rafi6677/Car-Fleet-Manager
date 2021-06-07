package com.example.carfleetmanager.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.carfleetmanager.data.model.Owner

@Dao
interface OwnersDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOwners(owners: List<Owner>)

    @Query("DELETE FROM owners")
    suspend fun deleteAllOwners()

    @Query("SELECT * FROM owners")
    suspend fun getAllOwners(): List<Owner>

    @Query("SELECT * FROM owners WHERE id = :id")
    suspend fun getOwnerById(id: String): Owner

}