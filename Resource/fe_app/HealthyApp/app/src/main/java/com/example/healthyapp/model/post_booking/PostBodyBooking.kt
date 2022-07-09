package com.example.healthyapp.model.post_booking


import com.google.gson.annotations.SerializedName

data class PostBodyBooking(
    @SerializedName("date")
    val date: String = "",
    @SerializedName("idDoctor")
    val idDoctor: Int = 0,
    @SerializedName("idWorktime")
    val idWorktime: Int = 0,
    @SerializedName("location")
    val location: String = "",
    @SerializedName("namePatient")
    val namePatient: String = "",
    @SerializedName("nameScheduler")
    val nameScheduler: String = "",
    @SerializedName("phonePatient")
    val phonePatient: String = "",
    @SerializedName("phoneScheduer")
    val phoneScheduer: String = "",
    @SerializedName("reason")
    val reason: String = "",
    @SerializedName("sex")
    val sex: String = "",
    @SerializedName("type")
    val type: String = "",
    @SerializedName("userId")
    val userId: Int = 0,
    @SerializedName("yearOfBirth")
    val yearOfBirth: String = ""
)