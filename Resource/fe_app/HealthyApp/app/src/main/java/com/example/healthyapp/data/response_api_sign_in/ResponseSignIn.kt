package com.example.healthyapp.data.response_api_sign_in


import com.google.gson.annotations.SerializedName

data class ResponseSignIn(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("password")
    val password: Any? = Any(),
    @SerializedName("roles")
    val roles: List<String> = listOf(),
    @SerializedName("token")
    val token: String = "",
    @SerializedName("type")
    val type: String = "",
    @SerializedName("usernamee")
    val usernamee: String = ""
)