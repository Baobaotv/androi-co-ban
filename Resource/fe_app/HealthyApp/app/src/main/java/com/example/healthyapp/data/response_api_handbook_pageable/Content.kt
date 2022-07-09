package com.example.healthyapp.data.response_api_handbook_pageable


import com.google.gson.annotations.SerializedName

data class Content(
    @SerializedName("content")
    val content: Any? = Any(),
    @SerializedName("createdBy")
    val createdBy: Any? = Any(),
    @SerializedName("createdById")
    val createdById: Any? = Any(),
    @SerializedName("createdByName")
    val createdByName: Any? = Any(),
    @SerializedName("createdDate")
    val createdDate: Any? = Any(),
    @SerializedName("description")
    val description: Any? = Any(),
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("img")
    val img: Any? = Any(),
    @SerializedName("modifiedBy")
    val modifiedBy: Any? = Any(),
    @SerializedName("modifiedDate")
    val modifiedDate: Any? = Any(),
    @SerializedName("specialized")
    val specialized: Any? = Any(),
    @SerializedName("specializedId")
    val specializedId: Any? = Any(),
    @SerializedName("title")
    val title: String = ""
)