package com.example.healthyapp.model.post_sign_in


import com.google.gson.annotations.SerializedName

data class PostBodySignIn(
    @SerializedName("username")
    val username: String = "",
    @SerializedName("password")
    val password: String = ""
)