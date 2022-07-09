package com.example.healthyapp.ui.fragments

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.healthyapp.R
import com.example.healthyapp.adapter.NewsHomeAdapter
import com.example.healthyapp.adapter.HorizontalAdapter
import com.example.healthyapp.data.AppApi
import com.example.healthyapp.data.response_detail_handbook.ResponseDetailHandBook
import com.example.healthyapp.data.response_detail_hospital.ResponseDetailHospital
import com.example.healthyapp.data.response_api_random_doctor.ResponseDoctor
import com.example.healthyapp.data.response_api_handbook.ResponseHandBook
import com.example.healthyapp.data.response_api_specialist_random.ResponseRandomSpecialist
import com.example.healthyapp.databinding.FragmentHomeBinding
import com.example.healthyapp.extensions.*
import com.example.healthyapp.helper.AppUtils
import com.example.healthyapp.helper.GlobalHelper
import com.example.healthyapp.helper.RetrofitInstance
import com.example.healthyapp.helper.SharedPreferenceHelper
import com.example.healthyapp.model.ItemNew
import com.example.healthyapp.model.ItemNewInternet
import com.example.healthyapp.ui.activities.*
import com.example.healthyapp.ui.base.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment:BaseFragment<FragmentHomeBinding>() {
    private lateinit var newsHomeAdapter: NewsHomeAdapter
    private lateinit var specialistsAdapter: HorizontalAdapter
    private lateinit var hospitalsAdapter: HorizontalAdapter
    private lateinit var doctorsAdapter: HorizontalAdapter
    private lateinit var handbooksAdapter: HorizontalAdapter
    private val api:AppApi by lazy { RetrofitInstance.getInstance()!!.api }
    private val sharedPref:SharedPreferenceHelper by lazy { SharedPreferenceHelper(requireContext()) }
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun onResume() {
        super.onResume()
        if(sharedPref.getValue(GlobalHelper.TOKEN_USER,"").toString().isNotEmpty()){
            binding.ibLogout.toVisible()
            binding.tvEmail.text = sharedPref.getValue(GlobalHelper.USER_NAME,"").toString()
            binding.tvName.text = sharedPref.getValue(GlobalHelper.NAME,"").toString()
        } else {
            binding.tvEmail.text = "Đăng ký"
            binding.tvName.text = "Đăng nhập"
            binding.ibLogout.toGone()
        }
    }

    override fun onSetupView() {

        setupRecyclerView()
        setupApi()
        setUpNavigateToViewContent()
        findOnClickWithScaleListener(binding.ibBookingScheduler,binding.imgAvatar,binding.tvName,binding.tvEmail,binding.tvMoreSpecialistTrend,binding.tvMoreHospitalTrend,binding.tvMoreDoctorTrend,binding.tvMoreHandBookTrend,binding.ibLogout){
            when(it){
                binding.tvName->operationTvName()
                binding.tvEmail->operationTvEmail()
                binding.tvMoreSpecialistTrend->(requireActivity() as MainActivity).changeTab(1)
                binding.tvMoreHospitalTrend->(requireActivity() as MainActivity).changeTab(2)
                binding.tvMoreDoctorTrend->(requireActivity() as MainActivity).changeTab(3)
                binding.tvMoreHandBookTrend->(requireActivity() as MainActivity).changeTab(4)
                binding.imgAvatar-> operationAvatar()
                binding.ibBookingScheduler-> startActivity(Intent(requireActivity(),ViewBookingByUserActivity::class.java))
                binding.ibLogout-> {
                    requireContext().showSuccessToast("Đăng xuất thành công")
                    sharedPref.putValue("",GlobalHelper.TOKEN_USER)
                    onResume()
                }
            }
        }
    }

    private fun operationAvatar() {
        if(sharedPref.getValue(GlobalHelper.TOKEN_USER,"").isEmpty()) return
        startActivity(Intent(requireActivity(),ProfileActivity::class.java)).also {
            requireActivity().overridePendingTransition(com.google.android.material.R.anim.mtrl_bottom_sheet_slide_in, com.google.android.material.R.anim.mtrl_bottom_sheet_slide_out)
        }
    }

    private fun setupApi() {
        api.getRandomSpecialist().enqueue(object:Callback<ResponseRandomSpecialist>{
            override fun onResponse(
                call: Call<ResponseRandomSpecialist>,
                response: Response<ResponseRandomSpecialist>
            ) {
                if(response.isSuccessful){
                    val listResponse = response.body()!!.map { ItemNewInternet(it.id,it.img,it.name,it.description) }
                    specialistsAdapter.submitList(listResponse)
                }
            }

            override fun onFailure(call: Call<ResponseRandomSpecialist>, t: Throwable) {
                requireContext().showErrorToast("Oops")
            }

        })

        api.getRandomHospital().enqueue(object:Callback<ResponseRandomSpecialist>{
            override fun onResponse(
                call: Call<ResponseRandomSpecialist>,
                response: Response<ResponseRandomSpecialist>
            ) {
                if(response.isSuccessful){
                    val listResponse = response.body()!!.map { ItemNewInternet(it.id,it.img,it.name,it.description) }
                    hospitalsAdapter.submitList(listResponse)
                }
            }

            override fun onFailure(call: Call<ResponseRandomSpecialist>, t: Throwable) {
                requireContext().showErrorToast("Oops")
            }

        })

        api.getRandomDoctor().enqueue(object:Callback<ResponseDoctor>{
            override fun onResponse(
                call: Call<ResponseDoctor>,
                response: Response<ResponseDoctor>
            ) {
                if(response.isSuccessful){
                    val listResponse = response.body()!!.map { ItemNewInternet(it.id,it.img,it.name,it.description) }
                    doctorsAdapter.submitList(listResponse)
                }
            }

            override fun onFailure(call: Call<ResponseDoctor>, t: Throwable) {
                requireContext().showErrorToast("Oops")
            }

        })

        api.getRandomHandBook().enqueue(object:Callback<ResponseHandBook>{
            override fun onResponse(
                call: Call<ResponseHandBook>,
                response: Response<ResponseHandBook>
            ) {
                if(response.isSuccessful){
                    val listResponse = response.body()!!.map { ItemNewInternet(it.id,it.img?:"",it.title,it.description) }
                    handbooksAdapter.submitList(listResponse)
                }
            }

            override fun onFailure(call: Call<ResponseHandBook>, t: Throwable) {
                requireContext().showErrorToast("Oops")
            }

        })
    }


    private fun setupRecyclerView() {
        if(!::newsHomeAdapter.isInitialized) newsHomeAdapter = NewsHomeAdapter()
        if(!::specialistsAdapter.isInitialized) specialistsAdapter = HorizontalAdapter()
        if(!::hospitalsAdapter.isInitialized) hospitalsAdapter = HorizontalAdapter()
        if(!::doctorsAdapter.isInitialized) doctorsAdapter = HorizontalAdapter()
        if(!::handbooksAdapter.isInitialized) handbooksAdapter = HorizontalAdapter()

        binding.rvNew.apply {
            itemAnimator = null
            adapter = newsHomeAdapter
            newsHomeAdapter.submitList(listOf(
                ItemNew(R.drawable.img_new_1,"Vì sao bị trầm cảm sau sinh"),
                ItemNew(R.drawable.img_new_2,"Khám và điều trị tăng huyết áp ở đâu tốt ở Hà Nội?"),
                ItemNew(R.drawable.img_new_3,"Review phòng khám chuyên khoa phụ sản Lotus Clinic và kinh nghiệm đi khám "),
                ItemNew(R.drawable.img_new_4,"Quy trình cắt bao quy đầu như thế nào? Cắt bao quy đầu có an toàn không?"),
            ))
        }

        binding.rvSpecilist.apply {
            itemAnimator = null
            adapter = specialistsAdapter
        }

        binding.rvHospital.apply {
            itemAnimator = null
            adapter = hospitalsAdapter
        }

        binding.rvDoctor.apply {
            itemAnimator = null
            adapter = doctorsAdapter
        }

        binding.rvHandbook.apply {
            itemAnimator = null
            adapter = handbooksAdapter
        }
    }

    private fun setUpNavigateToViewContent(){
        handbooksAdapter.setOnItemClickListener {
            if(it.id!=null)
            api.getHandBookDetailById(it.id!!).enqueue(object:Callback<ResponseDetailHandBook>{
                override fun onResponse(
                    call: Call<ResponseDetailHandBook>,
                    response: Response<ResponseDetailHandBook>
                ) {
                    if(response.isSuccessful){
                        val body = response.body()
                        body?.let {
                            AppUtils.startToViewContentActivity(requireActivity(),"<p><strong><font size=\"+3\">"+body.title+"</font></strong></p>"+body.description+body.content)
                        }
                    }else{
                        requireContext().showErrorToast("Xảy ra lỗi")
                    }
                }

                override fun onFailure(call: Call<ResponseDetailHandBook>, t: Throwable) {
                    requireContext().showErrorToast("Bài viết có vấn đề")
                }

            })
        }
        hospitalsAdapter.setOnItemClickListener {
            if(it.id!=null)
            api.getHospitalDetailById(it.id!!).enqueue(object:Callback<ResponseDetailHospital>{
                override fun onResponse(
                    call: Call<ResponseDetailHospital>,
                    response: Response<ResponseDetailHospital>
                ) {
                    if(response.isSuccessful){
                        val body = response.body()
                        body?.let {
                            AppUtils.startToViewContentActivity(requireActivity(),"<p><strong><font size=\"+3\">"+body.name+"</font></strong></p>"+"\n"+body.location+body.description,it.img)
                        }
                    }else{
                        requireContext().showErrorToast("Xảy ra lỗi")
                    }
                }

                override fun onFailure(call: Call<ResponseDetailHospital>, t: Throwable) {
                    requireContext().showErrorToast("Bài viết có vấn đề")
                }

            })
        }
    }

    private fun operationTvEmail() {
        if(sharedPref.getValue(GlobalHelper.TOKEN_USER,"").isNotEmpty()) return
        Intent(requireActivity(),SignUpActivity::class.java).also {
            startActivity(it)
            requireActivity().overridePendingTransition(com.google.android.material.R.anim.mtrl_bottom_sheet_slide_in, com.google.android.material.R.anim.mtrl_bottom_sheet_slide_out)
        }
    }

    private fun operationTvName() {
        if(sharedPref.getValue(GlobalHelper.TOKEN_USER,"").isNotEmpty()) return
        Intent(requireActivity(),SignInActivity::class.java).also {
            startActivity(it)
            requireActivity().overridePendingTransition(com.google.android.material.R.anim.mtrl_bottom_sheet_slide_in, com.google.android.material.R.anim.mtrl_bottom_sheet_slide_out)
        }
    }


}