package com.example.healthyapp.extensions

import com.google.android.material.snackbar.BaseTransientBottomBar

interface VoidCallback{
    fun execute()
}

interface StringCallback {
    fun execute(string:String?)
}

interface IntegerCallback {
    fun execute(int:Int?)
}

interface FloatCallback {
    fun execute(float:Float?)
}

interface DoubleCallback {
    fun execute(int:Double?)
}

interface BooleanCallback {
    fun execute(int:Boolean?)
}

interface SimpleCallback<T> {
    fun execute(data:T)
}

interface EventCallback<T>{
    fun onComplete(data:T)
    fun onError(error:String?)
}