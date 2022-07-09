package com.example.healthyapp.data.response_detail_user_booking


import com.google.gson.annotations.SerializedName

data class ResponseUserBookingItem(
    @SerializedName("date")
    val date: String = "",
    @SerializedName("doctor")
    val doctor: Doctor = Doctor(),
    @SerializedName("hospitalName")
    val hospitalName: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("location")
    val location: String = "",
    @SerializedName("namePatient")
    val namePatient: String = "",
    @SerializedName("nameScheduler")
    val nameScheduler: Any? = Any(),
    @SerializedName("phonePatient")
    val phonePatient: String = "",
    @SerializedName("phoneScheduer")
    val phoneScheduer: Any? = Any(),
    @SerializedName("reason")
    val reason: String = "",
    @SerializedName("sex")
    val sex: String = "",
    @SerializedName("status")
    val status: Int = 0,
    @SerializedName("type")
    val type: String = "",
    @SerializedName("wordTimeTime")
    val wordTimeTime: String? = null,
    @SerializedName("workTimeID")
    val workTimeID: Int = 0,
    @SerializedName("yearOfBirth")
    val yearOfBirth: String = ""
)