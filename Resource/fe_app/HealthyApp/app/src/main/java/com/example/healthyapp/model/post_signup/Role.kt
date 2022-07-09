package com.example.healthyapp.model.post_signup


import com.google.gson.annotations.SerializedName

data class Role(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("users")
    val users: List<String> = listOf()
)