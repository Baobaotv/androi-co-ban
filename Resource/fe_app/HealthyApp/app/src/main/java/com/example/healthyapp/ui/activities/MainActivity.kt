package com.example.healthyapp.ui.activities

import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.healthyapp.R
import com.example.healthyapp.adapter.MyPagerAdapter
import com.example.healthyapp.data.AppApi
import com.example.healthyapp.data.response_api_profile.ResponseProfile
import com.example.healthyapp.databinding.ActivityMainBinding
import com.example.healthyapp.extensions.showErrorToast
import com.example.healthyapp.extensions.showSuccessToast
import com.example.healthyapp.helper.GlobalHelper
import com.example.healthyapp.helper.RetrofitInstance
import com.example.healthyapp.helper.SharedPreferenceHelper
import com.example.healthyapp.ui.base.BaseActivity
import com.example.healthyapp.ui.fragments.*
import com.example.healthyapp.widgets.DepthPageTransformer
import nl.joery.animatedbottombar.AnimatedBottomBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity:BaseActivity<ActivityMainBinding>(){
    private lateinit var myPagerAdapter: MyPagerAdapter
    private val api:AppApi by lazy { RetrofitInstance.getInstance()!!.api }
    private val sharedPreferenceHelper:SharedPreferenceHelper by lazy { SharedPreferenceHelper(this) }
    override val bindingInflate: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun onBackPressed() {

    }

    override fun onSetupView() {
        binding.apply {
            myPagerAdapter = MyPagerAdapter(listOf(HomeFragment(),SpecialistFragment(),HospitalFragment(),DoctorFragment(),HandBookFragment()),supportFragmentManager,lifecycle)
            viewPager.apply {
                isUserInputEnabled = false
                adapter = myPagerAdapter
                offscreenPageLimit = 2
            }
            bottomBar.setOnTabSelectListener(object:AnimatedBottomBar.OnTabSelectListener{
                override fun onTabSelected(
                    lastIndex: Int,
                    lastTab: AnimatedBottomBar.Tab?,
                    newIndex: Int,
                    newTab: AnimatedBottomBar.Tab
                ) {
                    viewPager.currentItem = newIndex
                }
            })
        }
        if(sharedPreferenceHelper.getValue(GlobalHelper.TOKEN_USER,"").isNotEmpty()) api.getProfile("Bearer " + sharedPreferenceHelper.getValue(GlobalHelper.TOKEN_USER,"")).enqueue(object:Callback<ResponseProfile>{
            override fun onResponse(
                call: Call<ResponseProfile>,
                response: Response<ResponseProfile>
            ) {
                if(response.isSuccessful){
                    if(response.body()!=null){
                        sharedPreferenceHelper.putValue(response.body()!!,GlobalHelper.USER_PROFILE)
                        Log.d("TAG", "onResponse: ${response.body()!!}")

                    }else{
                        showErrorToast("Có lỗi xảy ra/nVui lòng thử lại")
                    }
                }else{
                    showErrorToast("Có lỗi xảy ra/nkiểm tra lại kết nối")
                }
            }

            override fun onFailure(call: Call<ResponseProfile>, t: Throwable) {
                showErrorToast("Có lỗi xảy ra/nkiểm tra lại kết nối")
            }

        })
    }

    fun changeTab(pos:Int) {
        binding.bottomBar.selectTabAt(pos,true)
    }

}