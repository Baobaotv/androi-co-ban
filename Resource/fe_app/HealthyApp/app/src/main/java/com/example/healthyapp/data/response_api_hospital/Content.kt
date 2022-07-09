package com.example.healthyapp.data.response_api_hospital


import com.google.gson.annotations.SerializedName

data class Content(
    @SerializedName("description")
    val description: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("img")
    val img: String = "",
    @SerializedName("location")
    val location: String = "",
    @SerializedName("name")
    val name: String = ""
)