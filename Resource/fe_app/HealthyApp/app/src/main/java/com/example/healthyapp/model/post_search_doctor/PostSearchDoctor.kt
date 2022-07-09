package com.example.healthyapp.model.post_search_doctor


import com.google.gson.annotations.SerializedName

data class PostSearchDoctor(
    @SerializedName("hospitalId")
    val hospitalId: Any? = "0",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("specializedId")
    val specializedId: Any? = ""
)