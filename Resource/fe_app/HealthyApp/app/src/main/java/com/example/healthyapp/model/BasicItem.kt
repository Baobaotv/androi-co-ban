package com.example.healthyapp.model

import androidx.annotation.DrawableRes

data class ItemNew(@DrawableRes var image:Int,var title:String,var nameDoctor:String?=null)
data class ItemNewInternet(var id:Int?=null,var image:String,var title:String,var des:String,var location:String?=null)
data class BasicItem(var id:Int?=null,val content:String)