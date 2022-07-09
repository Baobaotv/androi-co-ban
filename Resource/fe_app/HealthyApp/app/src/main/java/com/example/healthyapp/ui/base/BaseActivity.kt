package com.example.healthyapp.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.example.healthyapp.extensions.findOnClickListener
import com.example.healthyapp.extensions.getColorContextCompat
import com.example.healthyapp.helper.SharedPreferenceHelper
import java.util.*

abstract class BaseActivity<V:ViewBinding>: AppCompatActivity() {
    private var _binding:V?=null
    abstract val bindingInflate:(LayoutInflater)->V
    protected val binding get() = _binding as V
    protected val onBackPressed:(()->Unit)?=null
    private val preferenceHelper by lazy { SharedPreferenceHelper(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        _binding = bindingInflate.invoke(layoutInflater)
        setContentView(binding.root)
        onSetupView()
    }

    abstract fun onSetupView()
    protected fun onEventClickListener(){}

    override fun onBackPressed() {
        if(onBackPressed!=null) onBackPressed.invoke()
        else super.onBackPressed()
    }

    open fun setColorStatusBar(color:Int){
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = getColorContextCompat(color)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding =null
    }

}