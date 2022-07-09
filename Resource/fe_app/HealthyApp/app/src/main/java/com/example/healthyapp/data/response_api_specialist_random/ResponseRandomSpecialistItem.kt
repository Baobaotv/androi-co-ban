package com.example.healthyapp.data.response_api_specialist_random


import com.google.gson.annotations.SerializedName

data class ResponseRandomSpecialistItem(
    @SerializedName("code")
    val code: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("img")
    val img: String = "",
    @SerializedName("name")
    val name: String = ""
)