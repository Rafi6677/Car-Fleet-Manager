package com.example.carfleetmanager.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "owners")
data class Owner(
    @PrimaryKey
    @SerializedName("_id")
    val id: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("birth_date")
    val birthDate: String,
    @SerializedName("_mock")
    val mock: Boolean,
    @SerializedName("sex")
    val sex: String
) : Serializable