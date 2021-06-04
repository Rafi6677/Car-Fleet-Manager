package com.example.carfleetmanager.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "cars")
data class Car(
    @PrimaryKey
    @SerializedName("_id")
    val id: String,
    @SerializedName("brand")
    val brand: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("registration")
    val registration: String,
    @SerializedName("year")
    val year: String,
    @SerializedName("color")
    val color: String,
    @SerializedName("lat")
    val lat: String,
    @SerializedName("lng")
    val lng: String,
    @SerializedName("_mock")
    val mock: Boolean,
    @SerializedName("ownerId")
    val ownerId: String,
    @SerializedName("_recent")
    val recent: Boolean,
    @SerializedName("_recent_changed")
    val recentChanged: Boolean
) : Serializable