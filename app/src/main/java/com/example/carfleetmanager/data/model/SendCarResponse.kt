package com.example.carfleetmanager.data.model

import com.google.gson.annotations.SerializedName

data class SendCarResponse(
    @SerializedName("brand")
    val brand: String,
    @SerializedName("_changed")
    val changed: String,
    @SerializedName("_changedby")
    val changedby: String,
    @SerializedName("color")
    val color: String,
    @SerializedName("_created")
    val created: String,
    @SerializedName("_createdby")
    val createdby: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("_keywords")
    val keywords: List<String>,
    @SerializedName("model")
    val model: String,
    @SerializedName("registration")
    val registration: String,
    @SerializedName("_tags")
    val tags: String,
    @SerializedName("_version")
    val version: Int,
    @SerializedName("year")
    val year: String
)