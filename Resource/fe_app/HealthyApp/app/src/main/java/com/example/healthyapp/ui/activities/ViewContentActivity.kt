package com.example.healthyapp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.healthyapp.adapter.WorkTimeAdapter
import com.example.healthyapp.data.AppApi
import com.example.healthyapp.data.response_api_doctor.Content
import com.example.healthyapp.databinding.ActivityViewContentBinding
import com.example.healthyapp.extensions.showErrorToast
import com.example.healthyapp.extensions.showSuccessToast
import com.example.healthyapp.extensions.toGone
import com.example.healthyapp.extensions.toVisible
import com.example.healthyapp.helper.GlobalHelper
import com.example.healthyapp.helper.RetrofitInstance
import com.example.healthyapp.helper.SharedPreferenceHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewContentActivity : AppCompatActivity() {
    private lateinit var binding:ActivityViewContentBinding
    private var idDoctor = -1
    private val api: AppApi by lazy { RetrofitInstance.getInstance()!!.api }
    private lateinit var workTimeAdapter: WorkTimeAdapter
    private val sharedPref:SharedPreferenceHelper by lazy { SharedPreferenceHelper(this) }
    var img:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(intent.getIntExtra("idDoctor",-1)==-1){
            binding.nestedScrollView.toGone()
            intent?.let {
                if(!it.getStringExtra("image").isNullOrEmpty()){
                    binding.imgHeader.toVisible()
                    Glide.with(this).asBitmap().fitCenter().load(it.getStringExtra("image")).into(binding.imgHeader)
                }
                val html = it.getStringExtra("data")
                binding.webView.apply {
                    settings.apply {
                        javaScriptEnabled = true
                        setSupportZoom(true)
                        loadsImagesAutomatically = true
                        javaScriptCanOpenWindowsAutomatically = true
                    }
                    scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
                    if (html != null) {
                        loadData(html,"text/html","utf-8")
                    }
                }
            }
        } else{
            if(!::workTimeAdapter.isInitialized) workTimeAdapter = WorkTimeAdapter()
            binding.rvWorkTime.apply {
                itemAnimator = null
                adapter =  workTimeAdapter.also {
                    it.setOnItemClickListener {
                        val idWorkTime = it.id
                        val time  = it.time
                        val date = binding.calendar.dayOfMonth.toString()+"-"+(binding.calendar.month+1)+"-"+binding.calendar.year
                        if(sharedPref.getValue(GlobalHelper.TOKEN_USER,"").isNullOrEmpty()) {
                            showErrorToast("Vui lòng đăng nhập để đặt lịch khám!")
                            return@setOnItemClickListener
                        }
                        api.checkTimeBooking(sharedPref.getValue(GlobalHelper.TOKEN_USER,""),idDoctor,it.id,date).enqueue(object:Callback<Boolean>{
                            override fun onResponse(
                                call: Call<Boolean>,
                                response: Response<Boolean>
                            ) {
                                if(response.isSuccessful){
                                    if(response.body()==null) showErrorToast("Có lỗi xảy ra")
                                    else{
                                        if(!response.body()!!)
                                        {
                                            showSuccessToast("Đặt lịch khám ngay nào")
                                            startActivity(Intent(this@ViewContentActivity,BookingActivity::class.java).also {
                                                it.putExtra("time",time)
                                                it.putExtra("date",date)
                                                it.putExtra("name",binding.tvNameDoctor.text.toString())
                                                it.putExtra("specialist",binding.tvSpecialist.text.toString())
                                                it.putExtra("image",img)
                                                it.putExtra("idDoctor",idDoctor)
                                                it.putExtra("idWorkTime",idWorkTime)

                                            })
                                        }else showErrorToast("Ca khám đã có người đặt :(")
                                    }
                                }else showErrorToast("Có lỗi xảy ra")

                            }

                            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                                showErrorToast("Lỗi mạng")
                            }

                        })

                    }
                }
                layoutManager = LinearLayoutManager(this@ViewContentActivity,LinearLayoutManager.HORIZONTAL,false)
            }
            idDoctor = intent.getIntExtra("idDoctor",-1)
            api.getDoctorById(idDoctor).enqueue(object: Callback<Content> {
                override fun onResponse(
                    call: Call<Content>,
                    response: Response<Content>
                ) {
                    if(response.isSuccessful){
                        val body = response.body()
                        body?.let {
                                binding.apply {
                                    it.apply {
                                        workTimeAdapter.submitList(it.lstWorkTime)
                                        if(img.contains("gif"))
                                        Glide.with(this@ViewContentActivity).asGif().fitCenter().load(img).into(imgDoctor)
                                        else Glide.with(this@ViewContentActivity).asBitmap().fitCenter().load(img).into(imgDoctor)
                                        this@ViewContentActivity.img = img
                                        tvNameDoctor.text = "Tên bác sĩ: $name"
                                        tvGender.text = "Giới tính: $sex"
                                        tvPhoneNumbder.text = "Số điện thoại: $phone"
                                        tvLocation.text = "Địa chỉ bác sĩ: $location"
                                        tvDob.text = "Ngày sinh: $yearOfBirth"
                                        tvSpecialist.text = "Chuyên khoa: $specializedName"
                                        tvHospitalName.text = "Bệnh viện: $hospitalName"
                                        tvHospitalLocation.text = "Địa chỉ bệnh viện: $hospitalLocation"
                                        if(email.isNullOrEmpty()) tvEmail.toGone() else tvEmail.text = "Ngày sinh: $email"
                                        if(shortDescription.isNullOrEmpty()) tvDesSpecialist.toGone() else tvDesSpecialist.text = "Mô tả: ${HtmlCompat.fromHtml(shortDescription,FROM_HTML_MODE_LEGACY)}"
                                        binding.webViewDoctor.apply {
                                            settings.apply {
                                                javaScriptEnabled = true
                                                setSupportZoom(true)
                                                loadsImagesAutomatically = true
                                                javaScriptCanOpenWindowsAutomatically = true
                                            }
                                            val html = it.description
                                            scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
                                            loadData(html,"text/html","utf-8")
                                        }
                                    }
                                }

                        }
                    }else{
                        showErrorToast("Xảy ra lỗi")
                    }
                }

                override fun onFailure(call: Call<Content>, t: Throwable) {
                    showErrorToast("Không lấy được thông tin")
                }

            })
            binding.apply {
                tvBooking.setOnClickListener {
                    if(calendar.visibility == View.GONE) calendar.toVisible() else calendar.toGone()
                }
                calendar.apply {
                    maxDate = System.currentTimeMillis()+6*24*60*60*1000
                    minDate = System.currentTimeMillis()
                }
            }
        }
    }
}