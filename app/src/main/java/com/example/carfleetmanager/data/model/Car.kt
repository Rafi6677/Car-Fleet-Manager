package com.example.carfleetmanager.data.model


import com.google.gson.annotations.SerializedName

data class Car(
    @SerializedName("brand")
    val brand: String,
    @SerializedName("color")
    val color: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("lat")
    val lat: String,
    @SerializedName("lng")
    val lng: String,
    @SerializedName("_mock")
    val mock: Boolean,
    @SerializedName("model")
    val model: String,
    @SerializedName("ownerId")
    val ownerId: String,
    @SerializedName("_recent")
    val recent: Boolean,
    @SerializedName("_recent_changed")
    val recentChanged: Boolean,
    @SerializedName("registration")
    val registration: String,
    @SerializedName("year")
    val year: String
)