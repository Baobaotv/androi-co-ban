package com.example.healthyapp.ui.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.util.PatternsCompat
import com.example.healthyapp.R
import com.example.healthyapp.data.AppApi
import com.example.healthyapp.data.response_api_sign_in.ResponseSignIn
import com.example.healthyapp.databinding.ActivitySignInBinding
import com.example.healthyapp.extensions.*
import com.example.healthyapp.helper.GlobalHelper
import com.example.healthyapp.helper.RetrofitInstance
import com.example.healthyapp.helper.SharedPreferenceHelper
import com.example.healthyapp.model.post_sign_in.PostBodySignIn
import com.example.healthyapp.model.post_signup.PostBodySignUp
import com.example.healthyapp.ui.base.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : BaseActivity<ActivitySignInBinding>() {
    override val bindingInflate: (LayoutInflater) -> ActivitySignInBinding
        get() = ActivitySignInBinding::inflate

    private val pref: SharedPreferenceHelper by lazy { SharedPreferenceHelper(this) }
    private val api: AppApi by lazy { RetrofitInstance.getInstance()!!.api }

    override fun onSetupView() {
        binding.apply {
            val username = pref.getValue(GlobalHelper.USER_NAME,"")
            edtUserName.setText(username.toString())
            btnSignUp.setOnClickWithScaleListener {
                Intent(this@SignInActivity,SignUpActivity::class.java).also {
                    binding.loading.toGone()
                    startActivity(it)
                    overridePendingTransition(com.google.android.material.R.anim.mtrl_bottom_sheet_slide_in, com.google.android.material.R.anim.mtrl_bottom_sheet_slide_out)
                    finish()
                }
            }

            btnSignIn.setOnClickWithScaleListener {
                if (isEmpty(edtUserName) || isEmpty(edtPassWord)) {
                    showErrorToast("Không được để trống thông tin đăng nhập")
                    return@setOnClickWithScaleListener
                } else {
                        if (edtPassWord.text.toString().length < 6) {
                            showErrorToast("Mật khẩu ít nhất 6 ký tự")
                            return@setOnClickWithScaleListener
                        }
                        login(edtUserName.text.toString(), edtPassWord.text.toString())
                    }
                }
            }
    }

    private fun login(userName: String, password: String) {
        binding.loading.toVisible()
        api.signin(PostBodySignIn(userName,password)).enqueue(object: Callback<ResponseSignIn> {
            override fun onResponse(call: Call<ResponseSignIn>, response: Response<ResponseSignIn>) {
                if(response.isSuccessful){
                    binding.loading.toGone()
                    showSuccessToast("Đăng nhập thành công .")
                    pref.putValue(response.body()!!.token,GlobalHelper.TOKEN_USER)
                    pref.putValue(response.body()!!.id,GlobalHelper.USER_ID)
                    pref.putValue(password,GlobalHelper.PASSWORD+response.body()!!.usernamee)
                    startActivity(Intent(this@SignInActivity,MainActivity::class.java))
                } else{
                    binding.loading.toGone()
                    showErrorToast("Đăng nhập thất bại.\nSai tài khoản hoặc mật khẩu")
                }
            }

            override fun onFailure(call: Call<ResponseSignIn>, t: Throwable) {
                binding.loading.toGone()
                showErrorToast("Đăng nhập thất bại.")
            }

        })
    }

    private fun isEmpty(edt: AppCompatEditText): Boolean {
        return edt.text.toString().trim().isEmpty()
    }

}