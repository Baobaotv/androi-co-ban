package com.example.healthyapp.model.post_update_profile


import com.google.gson.annotations.SerializedName

data class PostUpdateProfile(
    @SerializedName("email")
    val email: String = "",
    @SerializedName("fullName")
    val fullName: String = "",
    @SerializedName("passwod")
    val password: String = "",
    @SerializedName("phone")
    val phone: String = ""
)