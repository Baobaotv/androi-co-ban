package com.example.healthyapp.ui.activities

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.util.PatternsCompat
import com.example.healthyapp.data.AppApi
import com.example.healthyapp.data.response_api_profile.ResponseProfile
import com.example.healthyapp.databinding.ActivitySignUpBinding
import com.example.healthyapp.extensions.*
import com.example.healthyapp.helper.GlobalHelper
import com.example.healthyapp.helper.RetrofitInstance
import com.example.healthyapp.helper.SharedPreferenceHelper
import com.example.healthyapp.model.post_signup.PostBodySignUp
import com.example.healthyapp.ui.base.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : BaseActivity<ActivitySignUpBinding>() {
    override val bindingInflate: (LayoutInflater) -> ActivitySignUpBinding
        get() = ActivitySignUpBinding::inflate
    private val api:AppApi by lazy { RetrofitInstance.getInstance()!!.api }
    private val pref:SharedPreferenceHelper by lazy { SharedPreferenceHelper(this) }
    override fun onSetupView() {
        binding.apply {
            btnSignIn.setOnClickWithScaleListener {
                Intent(this@SignUpActivity, SignInActivity::class.java).also {
                    binding.loading.toGone()
                    startActivity(it)
                    overridePendingTransition(
                        com.google.android.material.R.anim.mtrl_bottom_sheet_slide_in,
                        com.google.android.material.R.anim.mtrl_bottom_sheet_slide_out
                    )
                    finish()
                }
            }

            btnSignUp.setOnClickWithScaleListener {
                if (isEmpty(edtEmail) || isEmpty(edtName) || isEmpty(edtPhone) || isEmpty(
                        edtPassWord
                    ) || isEmpty(edtConfirmPassWord) || isEmpty(edtPhone)||isEmpty(edtUserName)
                ) {
                    showErrorToast("Kh??ng ???????c ????? tr???ng th??ng tin")
                    return@setOnClickWithScaleListener
                } else {
                    if (!PatternsCompat.EMAIL_ADDRESS.matcher(edtEmail.text.toString().trim())
                            .matches()
                    ) {
                        showErrorToast("Email kh??ng ????ng ?????nh d???ng")
                        return@setOnClickWithScaleListener
                    } else {
                        if (edtPassWord.text.toString().length < 6) {
                            showErrorToast("M???t kh???u ??t nh???t 6 k?? t???")
                            return@setOnClickWithScaleListener
                        } else if (edtPassWord.text.toString() != edtConfirmPassWord.text.toString()) {
                            showErrorToast("X??c nh???n m???t kh???u kh??ng ????ng")
                            return@setOnClickWithScaleListener
                        }
                        register()
                    }
                }
            }
        }
    }

    private fun register() {
        binding.loading.toVisible()
        val postBodySignUp = PostBodySignUp(
            name = binding.edtName.text.toString().trim(),
            username = binding.edtUserName.text.toString().trim(),
            email = binding.edtEmail.text.toString().trim(),
            phone = binding.edtPhone.text.toString().trim(),
            password = binding.edtPassWord.text.toString().trim(),
        )
        api.signup(postBodySignUp).enqueue(object:Callback<Any>{
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if(response.isSuccessful){
                    binding.loading.toGone()
                    showSuccessToast("????ng k?? th??nh c??ng .")
                    pref.putValue(binding.edtEmail.text.toString().trim(),GlobalHelper.EMAIL)
                    pref.putValue(binding.edtUserName.text.toString().trim(),GlobalHelper.USER_NAME)
                    pref.putValue(binding.edtName.text.toString().trim(),GlobalHelper.NAME)
                    pref.putValue(binding.edtPassWord.text.toString().trim(),GlobalHelper.PASSWORD+binding.edtUserName.text.toString().trim())
                    binding.btnSignIn.performClick()
                } else{
                    binding.loading.toGone()
                    showErrorToast("????ng k?? th???t b???i.\nT??i kho???n ???? t???n t???i")
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                binding.loading.toGone()
                showErrorToast("????ng k?? th???t b???i.")
            }

        })
    }


    private fun isEmpty(edt: AppCompatEditText): Boolean {
        return edt.text.toString().trim().isEmpty()
    }

}