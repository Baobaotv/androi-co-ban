package com.example.healthyapp.data.response_api_profile


import com.google.gson.annotations.SerializedName

data class ResponseProfile(
    @SerializedName("description")
    val description: Any? = Any(),
    @SerializedName("email")
    var email: String = "",
    @SerializedName("hospitalId")
    val hospitalId: Any? = Any(),
    @SerializedName("hospitalLocation")
    val hospitalLocation: Any? = Any(),
    @SerializedName("hospitalName")
    val hospitalName: Any? = Any(),
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("img")
    val img: Any? = Any(),
    @SerializedName("location")
    val location: String = "",
    @SerializedName("lstWorkTime")
    val lstWorkTime: List<Any> = listOf(),
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("password")
    var password: String? = null,
    @SerializedName("phone")
    var phone: String? = null,
    @SerializedName("reason")
    val reason: Any? = Any(),
    @SerializedName("role")
    val role: Any? = Any(),
    @SerializedName("roles")
    val roles: Any? = Any(),
    @SerializedName("sex")
    val sex: String = "",
    @SerializedName("shortDescription")
    val shortDescription: Any? = Any(),
    @SerializedName("specializedId")
    val specializedId: Any? = Any(),
    @SerializedName("specializedName")
    val specializedName: Any? = Any(),
    @SerializedName("username")
    val username: String = "",
    @SerializedName("yearOfBirth")
    val yearOfBirth: Any? = Any()
)