package com.example.healthyapp.helper

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferenceHelper(private val context:Context){
    private val sharedPref:SharedPreferences by lazy { context.getSharedPreferences(GlobalHelper.SHARED_NAME_APP, MODE_PRIVATE) }

    fun <T> putValue(value:T,name:String){
        when(value){
            is String -> sharedPref.edit().putString(name,value).apply()
            is Int -> sharedPref.edit().putInt(name,value).apply()
            is Float -> sharedPref.edit().putFloat(name,value).apply()
            is Long -> sharedPref.edit().putLong(name,value).apply()
            is Boolean -> sharedPref.edit().putBoolean(name,value).apply()
            else -> sharedPref.edit().putString(name,Gson().toJson(value)).apply()
        }
    }

    fun <T> getValue(name:String,type:T):T{
        return when(type){
            is String -> sharedPref.getString(name,"") as T
            is Int -> sharedPref.getInt(name,-1) as T
            is Float -> sharedPref.getFloat(name,-1f) as T
            is Long -> sharedPref.getLong(name,-1L) as T
            is Boolean -> sharedPref.getBoolean(name,false) as T
            else->{
                if(type is List<*>)
                Gson().fromJson(sharedPref.getString(name,""),object:TypeToken<List<T>>(){}.type) as T
                else Gson().fromJson(sharedPref.getString(name,""),type as Class<*>) as T
            }
        }
    }

}