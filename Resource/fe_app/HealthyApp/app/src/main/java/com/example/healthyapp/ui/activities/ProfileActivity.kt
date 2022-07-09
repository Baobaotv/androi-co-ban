package com.example.healthyapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.util.PatternsCompat
import com.example.healthyapp.R
import com.example.healthyapp.data.AppApi
import com.example.healthyapp.data.response_api_profile.ResponseProfile
import com.example.healthyapp.databinding.ActivityProfileBinding
import com.example.healthyapp.extensions.setOnClickWithScaleListener
import com.example.healthyapp.extensions.showErrorToast
import com.example.healthyapp.extensions.showSuccessToast
import com.example.healthyapp.helper.GlobalHelper
import com.example.healthyapp.helper.RetrofitInstance
import com.example.healthyapp.helper.SharedPreferenceHelper
import com.example.healthyapp.model.post_update_profile.PostUpdateProfile
import com.example.healthyapp.ui.base.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : BaseActivity<ActivityProfileBinding>() {
    override val bindingInflate: (LayoutInflater) -> ActivityProfileBinding
        get() = ActivityProfileBinding::inflate
    private lateinit var responseUserProfile: ResponseProfile
    private val api: AppApi by lazy { RetrofitInstance.getInstance()!!.api }
    private val sharedPref: SharedPreferenceHelper by lazy { SharedPreferenceHelper(this) }
    override fun onSetupView() {
        responseUserProfile = sharedPref.getValue(GlobalHelper.USER_PROFILE,ResponseProfile::class.java) as ResponseProfile
        binding.apply {
            edtName.setText(responseUserProfile.name)
            edtEmail.setText(responseUserProfile.email)
            edtPhone.setText(responseUserProfile.phone)
            cbPassword.setOnCheckedChangeListener { _, b ->
                lyPassword.visibility = if(b) View.VISIBLE else View.GONE
            }
            btnUpdate.setOnClickWithScaleListener {
                if(!cbPassword.isChecked){
                    if(checkIsEmpty(edtEmail,edtName,edtPhone)){
                        showErrorToast("Không được để trống!!")
                        return@setOnClickWithScaleListener
                    }
                    if (!PatternsCompat.EMAIL_ADDRESS.matcher(edtEmail.text.toString().trim())
                            .matches()
                    ) {
                        showErrorToast("Email không đúng định dạng")
                        return@setOnClickWithScaleListener
                    }
                    updateProfile(cbPassword.isChecked)
                }else{
                    if(checkIsEmpty(edtEmail,edtName,edtPhone,edtPassWord,edtConfirmPassWord)){
                        showErrorToast("Không được để trống!!")
                        return@setOnClickWithScaleListener
                    }
                    if (!PatternsCompat.EMAIL_ADDRESS.matcher(edtEmail.text.toString().trim())
                            .matches()
                    ) {
                        showErrorToast("Email không đúng định dạng")
                        return@setOnClickWithScaleListener
                    }
                    if (edtPassWord.text.toString().length < 6) {
                        showErrorToast("Mật khẩu ít nhất 6 ký tự")
                        return@setOnClickWithScaleListener
                    } else if (edtPassWord.text.toString() != edtConfirmPassWord.text.toString()) {
                        showErrorToast("Xác nhận mật khẩu không đúng")
                        return@setOnClickWithScaleListener
                    }
                    updateProfile(cbPassword.isChecked)
                }


            }
        }
    }

    private fun updateProfile(isChecked: Boolean) {
        responseUserProfile.name = binding.edtName.text.toString().trim()
        responseUserProfile.email = binding.edtEmail.text.toString().trim()
        responseUserProfile.phone = binding.edtPhone.text.toString().trim()
        var password = ""
        if(isChecked) password = binding.edtPassWord.text.toString().trim() else password = sharedPref.getValue(GlobalHelper.PASSWORD+GlobalHelper.USER_NAME,"")
        api.updateClient("Bearer " + sharedPref.getValue(GlobalHelper.TOKEN_USER,""),
            PostUpdateProfile(responseUserProfile.email, responseUserProfile.name!!,password,responseUserProfile.phone!!)
        ).enqueue(object:Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    if(response.body()==true){
                        sharedPref.putValue(responseUserProfile,GlobalHelper.USER_PROFILE)
                        sharedPref.putValue(responseUserProfile.name,GlobalHelper.NAME)
                        showSuccessToast("Cập nhật thành công")
                    }else{
                        showErrorToast("Cập nhật thất bại.\nThử lại")
                    }
                }else{
                    showErrorToast("Cập nhật thất bại.\nThử lại")
                    return
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                showErrorToast("Cập nhật thất bại.\nKiểm tra lại kết nối")
                return
            }

        })
    }

    private fun checkIsEmpty(vararg listEditText: AppCompatEditText):Boolean{
        return listEditText.any { it.text.toString().trim().isEmpty() }
    }

}