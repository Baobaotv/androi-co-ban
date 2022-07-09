package com.example.healthyapp.ui.fragments

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.healthyapp.R
import com.example.healthyapp.adapter.FullWidthHorizontalAdapter
import com.example.healthyapp.data.AppApi
import com.example.healthyapp.data.response_api_hospital.ResponseHospital
import com.example.healthyapp.databinding.FragmentHospitalBinding
import com.example.healthyapp.extensions.showErrorToast
import com.example.healthyapp.helper.AppUtils
import com.example.healthyapp.helper.RetrofitInstance
import com.example.healthyapp.model.ItemNewInternet
import com.example.healthyapp.ui.activities.ViewAllDoctorsActivity
import com.example.healthyapp.ui.base.BaseFragment
import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HospitalFragment:BaseFragment<FragmentHospitalBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHospitalBinding
        get() = FragmentHospitalBinding::inflate
    private lateinit var hospitalsAdapterFullWidth: FullWidthHorizontalAdapter
    private val api: AppApi by lazy { RetrofitInstance.getInstance()!!.api }
    private val DEFAULT_PAGE_INDEX = 0
    private var pageSize = 3
    private var isLoading = false
    override fun onSetupView() {
        setupRecyclerView()
        callApi()

    }

    private fun callApi(){
        isLoading = true
        api.getHospital(DEFAULT_PAGE_INDEX,pageSize).enqueue(object: Callback<ResponseHospital> {
            override fun onResponse(
                call: Call<ResponseHospital>,
                response: Response<ResponseHospital>
            ) {
                if(response.isSuccessful){
                    isLoading = false
                    val listResponse = response.body()!!.content.map { ItemNewInternet(it.id,it.img,it.name, it.description,it.location) }
                    hospitalsAdapterFullWidth.submitList(listResponse)
                }
            }

            override fun onFailure(call: Call<ResponseHospital>, t: Throwable) {
                isLoading = false
                requireContext().showErrorToast("Oops")
            }

        })

    }

    private fun setupRecyclerView() {
        if(!::hospitalsAdapterFullWidth.isInitialized) hospitalsAdapterFullWidth = FullWidthHorizontalAdapter()
        binding.rvHospital.apply {
            itemAnimator = null
            adapter = hospitalsAdapterFullWidth.also {
                it.setOnItemClickListener { data->
                    val mBottomSheetDialog = BottomSheetMaterialDialog.Builder(requireActivity())
                        .setTitle("Tính năng!")
                        .setMessage("Lựa chọn yêu cầu bên dưới ?")
                        .setCancelable(false)
                        .setPositiveButton(
                            "Xem bác sĩ"
                        ) { dialogInterface, which ->
                            startActivity(Intent(requireActivity(),ViewAllDoctorsActivity::class.java).also {
                                it.putExtra("id",data.id)
                                it.putExtra("type","hospital")
                            })
                            dialogInterface.dismiss()
                        }
                        .setNegativeButton(
                            "Xem bệnh viện"
                        ) { dialogInterface, which ->
                            AppUtils.startToViewContentActivity(requireActivity(),"<p><strong><font size=\"+3\">"+data.title+"</font></strong></p>"+"\n"+"<p><strong>"+"Địa chỉ : "+data.location+"</strong></p>"+"\n"+data.des,data.image)
                            dialogInterface.dismiss()
                        }
                        .build()
                    mBottomSheetDialog.show()

                }
            }
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if(!recyclerView.canScrollVertically(1)){
                        if(isLoading) return
                        pageSize+=3
                        callApi()
                    }
                    super.onScrolled(recyclerView, dx, dy)
                }
            })
        }
    }
}