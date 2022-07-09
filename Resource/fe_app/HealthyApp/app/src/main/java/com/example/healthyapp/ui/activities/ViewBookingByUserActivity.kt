package com.example.healthyapp.ui.activities

import android.view.LayoutInflater
import com.example.healthyapp.adapter.BookingDetailAdapter
import com.example.healthyapp.data.AppApi
import com.example.healthyapp.data.response_detail_user_booking.ResponseUserBooking
import com.example.healthyapp.databinding.ActivityViewBookingByUserBinding
import com.example.healthyapp.extensions.showErrorToast
import com.example.healthyapp.helper.GlobalHelper
import com.example.healthyapp.helper.RetrofitInstance
import com.example.healthyapp.helper.SharedPreferenceHelper
import com.example.healthyapp.ui.base.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewBookingByUserActivity : BaseActivity<ActivityViewBookingByUserBinding>() {
    override val bindingInflate: (LayoutInflater) -> ActivityViewBookingByUserBinding
        get() = ActivityViewBookingByUserBinding::inflate
    private val api: AppApi by lazy { RetrofitInstance.getInstance()!!.api }
    private val sharedPreferenceHelper: SharedPreferenceHelper by lazy { SharedPreferenceHelper(this) }
    private lateinit var bookingDetailAdapter: BookingDetailAdapter
    override fun onSetupView() {
        if(!::bookingDetailAdapter.isInitialized) bookingDetailAdapter = BookingDetailAdapter()
        api.getBookingDetail("Bearer "+sharedPreferenceHelper.getValue(GlobalHelper.TOKEN_USER,"")).enqueue(object:Callback<ResponseUserBooking>{
            override fun onResponse(
                call: Call<ResponseUserBooking>,
                response: Response<ResponseUserBooking>
            ) {
                if(response.isSuccessful&&response.body()!=null){
                    binding.rvBoookings.apply {
                        itemAnimator = null
                        setHasFixedSize(true)
                        adapter = bookingDetailAdapter.also { it.submitList(response.body()!!) }
                    }
                }else{
                    showErrorToast("Vui lòng thử lại!")
                }
            }

            override fun onFailure(call: Call<ResponseUserBooking>, t: Throwable) {
                showErrorToast("Kiểm tra lại kết nối")
                return
            }

        })
    }

}