package com.example.healthyapp.data.response_api_random_doctor


import com.google.gson.annotations.SerializedName

data class LstWorkTime(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("time")
    val time: String = ""
)