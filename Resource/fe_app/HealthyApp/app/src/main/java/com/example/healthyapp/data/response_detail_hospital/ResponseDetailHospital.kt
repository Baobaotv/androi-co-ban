package com.example.healthyapp.data.response_detail_hospital


import com.google.gson.annotations.SerializedName

data class ResponseDetailHospital(
    @SerializedName("description")
    val description: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("img")
    val img: String = "",
    @SerializedName("location")
    val location: String = "",
    @SerializedName("lstuser")
    val lstuser: List<Any> = listOf(),
    @SerializedName("name")
    val name: String = "",
    @SerializedName("status")
    val status: Int = 0
)