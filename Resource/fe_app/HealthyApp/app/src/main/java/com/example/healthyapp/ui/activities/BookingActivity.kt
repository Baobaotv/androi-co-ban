package com.example.healthyapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.healthyapp.R
import com.example.healthyapp.data.AppApi
import com.example.healthyapp.databinding.ActivityBookingBinding
import com.example.healthyapp.extensions.*
import com.example.healthyapp.helper.GlobalHelper
import com.example.healthyapp.helper.RetrofitInstance
import com.example.healthyapp.helper.SharedPreferenceHelper
import com.example.healthyapp.model.post_booking.PostBodyBooking
import com.example.healthyapp.ui.base.BaseActivity
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookingActivity : BaseActivity<ActivityBookingBinding>() {

    private val api:AppApi by lazy { RetrofitInstance.getInstance()!!.api }
    override val bindingInflate: (LayoutInflater) -> ActivityBookingBinding
        get() = ActivityBookingBinding::inflate
    private val sharedPreferenceHelper:SharedPreferenceHelper by lazy { SharedPreferenceHelper(this) }
    override fun onSetupView() {
        binding.apply {
            edtNameScheduler.toGone()
            edtPhone.toGone()
            intent.getStringExtra("image")?.let {
                if(it.contains("gif"))
                    Glide.with(this@BookingActivity).asGif().fitCenter().load(it).into(imgAvatar)
                else Glide.with(this@BookingActivity).asBitmap().fitCenter().load(it).into(imgAvatar)
            }
            intent.getStringExtra("name").let {
                binding.tvName.text = it
            }

            intent.getStringExtra("specialist").let {
                binding.tvSpecialist.text = it
            }

            intent.apply {
                val contentDate = getStringExtra("time")+", ngày "+getStringExtra("date")
                binding.tvTime.text = contentDate
            }
            btnBooking.setOnClickWithScaleListener {
                if(binding.rdMe.isChecked){
                    if(checkIsEmpty(edtAddress,edtReason,edtPhoneContact,edtPatient,edtDob)){
                        this@BookingActivity.showErrorToast("Không được để trống bất kỳ trường nào")
                    }else{
                        booking()
                    }
                }else{
                    if(checkIsEmpty(edtAddress,edtReason,edtPhoneContact,edtPatient,edtDob,edtNameScheduler,edtPhone)) {
                        this@BookingActivity.showErrorToast("Không được để trống bất kỳ trường nào")
                    }else{
                        booking()
                    }
                }
            }
        }
        controlUi()
    }

    private fun booking(){
        val postBodyBooking = PostBodyBooking(
            intent.getStringExtra("date")!!,
            intent.getIntExtra("idDoctor",-1),
            intent.getIntExtra("idWorkTime",-1),
            binding.edtAddress.text.toString().trim(),
            binding.edtPatient.text.toString().trim(),
            binding.edtNameScheduler.text.toString().trim(),
            binding.edtPhone.text.toString().trim(),
            binding.edtPhoneContact.text.toString().trim(),
            binding.edtReason.text.toString().trim(),
            if(binding.rdFemale.isChecked) "Nữ" else "Nam",
            if(binding.rdOnline.isChecked) "on" else "off",
            sharedPreferenceHelper.getValue(GlobalHelper.USER_ID,0),
            binding.edtDob.text.toString()
        )
        api.booking(sharedPreferenceHelper.getValue(GlobalHelper.TOKEN_USER,""),postBodyBooking).enqueue(object:Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    if(response.body()==true){
                        showSuccessToast("Đặt lịch khám thành công")
                        finish()
                    }else{
                        showErrorToast("Đặt lịch khám thất bại.\nThử lại ")
                    }
                }else{
                    showErrorToast("Đặt lịch khám thất bại.\nThử lại ")
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                showErrorToast("Kiểm tra lại kết nối mạng")
            }

        })
    }

    private fun controlUi() {
        binding.apply {
            rdMale.setOnCheckedChangeListener { _, b -> rdFemale.isChecked = !b }
            rdFemale.setOnCheckedChangeListener { _, b -> rdMale.isChecked = !b }
            rdOffline.setOnCheckedChangeListener { _, b ->  rdOnline.isChecked = !b}
            rdOnline.setOnCheckedChangeListener { _, b ->  rdOffline.isChecked = !b}
            rdMe.setOnCheckedChangeListener { _, b ->
                rdOther.isChecked = !b
            }

            rdOther.setOnCheckedChangeListener { _, b ->
                rdMe.isChecked = !b
                if(rdOther.isChecked){
                    edtNameScheduler.toVisible()
                    edtPhone.toVisible()
                } else{
                    edtNameScheduler.toGone()
                    edtPhone.toGone()
                }
            }
        }
    }

    private fun checkIsEmpty(vararg listEditText:AppCompatEditText):Boolean{
        return listEditText.any { it.text.toString().trim().isEmpty() }
    }
}