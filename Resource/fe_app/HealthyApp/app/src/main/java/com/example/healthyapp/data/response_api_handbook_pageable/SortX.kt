package com.example.healthyapp.data.response_api_handbook_pageable


import com.google.gson.annotations.SerializedName

data class SortX(
    @SerializedName("empty")
    val empty: Boolean = false,
    @SerializedName("sorted")
    val sorted: Boolean = false,
    @SerializedName("unsorted")
    val unsorted: Boolean = false
)