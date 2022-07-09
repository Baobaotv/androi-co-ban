package com.example.healthyapp.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.healthyapp.R
import com.example.healthyapp.helper.AnimationHelper
import io.github.muddz.styleabletoast.StyleableToast

fun Context.getColorContextCompat(color:Int) = ContextCompat.getColor(this,color)
fun Activity.getColorContextCompat(color:Int) = ContextCompat.getColor(this,color)
fun Fragment.getColorContextCompat(color:Int) = ContextCompat.getColor(this.requireContext(),color)

fun Context.showSystemToast(msg:String) = Toast.makeText(this,msg,Toast.LENGTH_SHORT)
fun Context.showErrorToast(msg:String) = StyleableToast.makeText(this,msg, R.style.toast_error).show()
fun Context.showSuccessToast(msg:String) = StyleableToast.makeText(this,msg, R.style.toast_success).show()

fun Fragment.findOnClickListener(vararg views:View,event:(View.OnClickListener)){
    views.forEach {
        it.setOnClickListener(event)
    }
}
fun Fragment.findOnClickWithScaleListener(vararg views:View,event:(View)->Unit){
    views.forEach {
        it.setOnClickListener {
            AnimationHelper.scaleAnimation(it,object:VoidCallback{
                override fun execute() {
                    event.invoke(it)
                }

            })
        }
    }
}

fun Activity.findOnClickListener(vararg views:View,event:(View.OnClickListener)){
    views.forEach {
        it.setOnClickListener(event)
    }
}
fun Activity.findOnClickWithScaleListener(vararg views:View,event:(View)->Unit){
    views.forEach {
        it.setOnClickListener {
            AnimationHelper.scaleAnimation(it,object:VoidCallback{
                override fun execute() {
                    event.invoke(it)
                }
            })
        }
    }
}

fun View.setOnClickWithScaleListener(event: () -> Unit){
    this.setOnClickListener {
        AnimationHelper.scaleAnimation(it,object:VoidCallback{
            override fun execute() {
                event.invoke()
            }
        })
    }
}