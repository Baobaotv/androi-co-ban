package com.example.healthyapp.data.response_api_handbook


import com.google.gson.annotations.SerializedName

data class ResponseHandBookItem(
    @SerializedName("content")
    val content: String = "",
    @SerializedName("createdBy")
    val createdBy: String = "",
    @SerializedName("createdById")
    val createdById: Int = 0,
    @SerializedName("createdByName")
    val createdByName: String = "",
    @SerializedName("createdDate")
    val createdDate: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("img")
    var img: String?=null,
    @SerializedName("modifiedBy")
    val modifiedBy: String = "",
    @SerializedName("modifiedDate")
    val modifiedDate: String = "",
    @SerializedName("specialized")
    val specialized: String = "",
    @SerializedName("specializedId")
    val specializedId: Int = 0,
    @SerializedName("title")
    val title: String = ""
)