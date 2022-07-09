package com.example.healthyapp.data.response_api_hospital


import com.google.gson.annotations.SerializedName

data class Sort(
    @SerializedName("empty")
    val empty: Boolean = false,
    @SerializedName("sorted")
    val sorted: Boolean = false,
    @SerializedName("unsorted")
    val unsorted: Boolean = false
)