package com.example.healthyapp.data.response_detail_user_booking


import com.google.gson.annotations.SerializedName

data class Doctor(
    @SerializedName("description")
    val description: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("hospitalId")
    val hospitalId: Int = 0,
    @SerializedName("hospitalLocation")
    val hospitalLocation: String = "",
    @SerializedName("hospitalName")
    val hospitalName: String = "",
    @SerializedName("id")
    val id: Int = -1,
    @SerializedName("img")
    val img: String = "",
    @SerializedName("location")
    val location: String = "",
    @SerializedName("lstWorkTime")
    val lstWorkTime: List<LstWorkTime> = listOf(),
    @SerializedName("name")
    val name: String = "",
    @SerializedName("password")
    val password: Any? = Any(),
    @SerializedName("phone")
    val phone: String = "",
    @SerializedName("reason")
    val reason: Any? = Any(),
    @SerializedName("role")
    val role: Any? = Any(),
    @SerializedName("roles")
    val roles: Any? = Any(),
    @SerializedName("sex")
    val sex: String = "",
    @SerializedName("shortDescription")
    val shortDescription: String = "",
    @SerializedName("specializedId")
    val specializedId: Int = 0,
    @SerializedName("specializedName")
    val specializedName: String = "",
    @SerializedName("username")
    val username: String = "",
    @SerializedName("yearOfBirth")
    val yearOfBirth: String = ""
)