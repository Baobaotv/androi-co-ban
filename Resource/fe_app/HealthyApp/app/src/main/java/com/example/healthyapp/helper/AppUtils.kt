package com.example.healthyapp.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.example.healthyapp.ui.activities.ViewContentActivity

class AppUtils {
    companion object{
        fun startToViewContentActivity(activity:Activity,data:String){
            Intent(activity, ViewContentActivity::class.java).also {
                it.putExtra("data", data)
                activity.startActivity(it)
                activity.overridePendingTransition(com.google.android.material.R.anim.mtrl_bottom_sheet_slide_in, com.google.android.material.R.anim.mtrl_bottom_sheet_slide_out)
            }
        }
        fun startToViewContentActivity(activity:Activity,data:String,image:String){
            Intent(activity, ViewContentActivity::class.java).also {
                it.putExtra("data", data)
                it.putExtra("image", image)
                activity.startActivity(it)
                activity.overridePendingTransition(com.google.android.material.R.anim.mtrl_bottom_sheet_slide_in, com.google.android.material.R.anim.mtrl_bottom_sheet_slide_out)
            }
        }

        fun startToViewContentActivity(activity:Activity,idDoctor:Int){
            Intent(activity, ViewContentActivity::class.java).also {
                it.putExtra("idDoctor", idDoctor)
                activity.startActivity(it)
                activity.overridePendingTransition(com.google.android.material.R.anim.mtrl_bottom_sheet_slide_in, com.google.android.material.R.anim.mtrl_bottom_sheet_slide_out)
            }
        }
    }
}