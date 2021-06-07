package com.example.carfleetmanager.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SendCar(
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
    @SerializedName("ownerId")
    val ownerId: String,
) : Serializable