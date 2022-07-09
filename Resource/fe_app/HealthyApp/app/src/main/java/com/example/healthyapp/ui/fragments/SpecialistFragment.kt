package com.example.healthyapp.ui.fragments

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.healthyapp.R
import com.example.healthyapp.adapter.FullWidthHorizontalAdapter
import com.example.healthyapp.data.AppApi
import com.example.healthyapp.data.response_api_handbook_pageable.ResponseHandBookPageable
import com.example.healthyapp.data.response_api_specialist.ResponseSpecialist
import com.example.healthyapp.data.response_api_specialist_random.ResponseRandomSpecialist
import com.example.healthyapp.databinding.FragmentSpecialistBinding
import com.example.healthyapp.extensions.showErrorToast
import com.example.healthyapp.helper.RetrofitInstance
import com.example.healthyapp.model.ItemNew
import com.example.healthyapp.model.ItemNewInternet
import com.example.healthyapp.ui.activities.ViewAllDoctorsActivity
import com.example.healthyapp.ui.base.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SpecialistFragment:BaseFragment<FragmentSpecialistBinding>(){
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSpecialistBinding
        get() = FragmentSpecialistBinding::inflate
    private lateinit var specialistsAdapterFullWidth: FullWidthHorizontalAdapter
    private val api: AppApi by lazy { RetrofitInstance.getInstance()!!.api }
    private val DEFAULT_PAGE_INDEX = 0
    private var pageSize = 3
    private var isLoading = false
    override fun onSetupView() {
        binding.apply {
            if(!::specialistsAdapterFullWidth.isInitialized) specialistsAdapterFullWidth = FullWidthHorizontalAdapter()
            binding.rvSpecilist.apply {
                itemAnimator = null
                adapter = specialistsAdapterFullWidth.also {item->
                    item.setOnItemClickListener {data->
                        startActivity(Intent(requireActivity(),ViewAllDoctorsActivity::class.java).also {
                            it.putExtra("id",data.id)
                            it.putExtra("type","specialist")
                        })
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
            callApi()
        }
    }

    private fun callApi(){
        isLoading = true
        api.getSpecialist(DEFAULT_PAGE_INDEX,pageSize).enqueue(object: Callback<ResponseSpecialist> {
            override fun onResponse(
                call: Call<ResponseSpecialist>,
                response: Response<ResponseSpecialist>
            ) {
                if(response.isSuccessful){
                    isLoading = false
                    val listResponse = response.body()!!.content.map { ItemNewInternet(it.id,it.img,it.name, it.description) }
                    specialistsAdapterFullWidth.submitList(listResponse)
                }
            }

            override fun onFailure(call: Call<ResponseSpecialist>, t: Throwable) {
                isLoading = false
                requireContext().showErrorToast("Oops")
            }

        })

    }

}