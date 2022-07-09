package com.example.healthyapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.example.healthyapp.R
import com.example.healthyapp.adapter.DoctorsAdapter
import com.example.healthyapp.data.AppApi
import com.example.healthyapp.data.response_api_doctor.ResponseApiDoctor
import com.example.healthyapp.data.response_api_specialist.ResponseSpecialist
import com.example.healthyapp.databinding.ActivityViewAllDoctorsBinding
import com.example.healthyapp.extensions.showErrorToast
import com.example.healthyapp.helper.AppUtils
import com.example.healthyapp.helper.RetrofitInstance
import com.example.healthyapp.model.ItemNewInternet
import com.example.healthyapp.ui.base.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewAllDoctorsActivity : BaseActivity<ActivityViewAllDoctorsBinding>() {
    override val bindingInflate: (LayoutInflater) -> ActivityViewAllDoctorsBinding
        get() = ActivityViewAllDoctorsBinding::inflate
    private var id = -1
    private val api: AppApi by lazy { RetrofitInstance.getInstance()!!.api }
    private val DEFAULT_PAGE_INDEX = 0
    private var pageSize = 3
    private var isLoading = false
    private var type = ""
    private lateinit var doctorsAdapter:DoctorsAdapter
    override fun onSetupView() {
        if(intent==null) return
        id = intent.getIntExtra("id",-1)
        type = intent.getStringExtra("type")!!
        if(!::doctorsAdapter.isInitialized) doctorsAdapter = DoctorsAdapter()
        binding.rcDoctors.apply {
            itemAnimator = null
            adapter = doctorsAdapter.also {
                it.setOnItemClickListener {
                    Log.d("TAG", "onSetupView: ${it.id}")
                    AppUtils.startToViewContentActivity(this@ViewAllDoctorsActivity, it.id)
                }
            }
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if(!recyclerView.canScrollVertically(1)){
                        if(isLoading) return
                        pageSize+=3
                        callApi(type)
                    }
                    super.onScrolled(recyclerView, dx, dy)
                }
            })
        }
        callApi(type)
    }

    private fun callApi(type: String) {
        isLoading = true
        when(type){
            "specialist"->{
                api.getDoctorsInSpecialist(id,DEFAULT_PAGE_INDEX,pageSize).enqueue(object:
                    Callback<ResponseApiDoctor> {
                    override fun onResponse(
                        call: Call<ResponseApiDoctor>,
                        response: Response<ResponseApiDoctor>
                    ) {
                        if(response.isSuccessful){
                            isLoading = false
                            if(response.body()==null) {
                                showErrorToast("Có lỗi xảy ra")
                                return
                            }
                            doctorsAdapter.submitList(response.body()!!.content)
                        }
                    }

                    override fun onFailure(call: Call<ResponseApiDoctor>, t: Throwable) {
                        isLoading = false
                        showErrorToast("Oops")
                    }

                })
            }

            "hospital"->{
                api.getDoctorsInHospital(id,DEFAULT_PAGE_INDEX,pageSize).enqueue(object:
                    Callback<ResponseApiDoctor> {
                    override fun onResponse(
                        call: Call<ResponseApiDoctor>,
                        response: Response<ResponseApiDoctor>
                    ) {
                        if(response.isSuccessful){
                            isLoading = false
                            if(response.body()==null) {
                                showErrorToast("Có lỗi xảy ra")
                                return
                            }
                            doctorsAdapter.submitList(response.body()!!.content)
                        }
                    }

                    override fun onFailure(call: Call<ResponseApiDoctor>, t: Throwable) {
                        isLoading = false
                        showErrorToast("Oops")
                    }

                })
            }
        }
    }

}