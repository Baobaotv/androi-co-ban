package com.example.healthyapp.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.healthyapp.adapter.FullWidthHorizontalAdapter
import com.example.healthyapp.data.AppApi
import com.example.healthyapp.data.response_detail_handbook.ResponseDetailHandBook
import com.example.healthyapp.data.response_api_handbook_pageable.ResponseHandBookPageable
import com.example.healthyapp.databinding.FragmentHandBookBinding
import com.example.healthyapp.extensions.showErrorToast
import com.example.healthyapp.helper.AppUtils
import com.example.healthyapp.helper.RetrofitInstance
import com.example.healthyapp.model.ItemNewInternet
import com.example.healthyapp.ui.base.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HandBookFragment:BaseFragment<FragmentHandBookBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHandBookBinding
        get() = FragmentHandBookBinding::inflate
    private lateinit var handBookAdapter:FullWidthHorizontalAdapter
    private val api: AppApi by lazy { RetrofitInstance.getInstance()!!.api }
    private val DEFAULT_PAGE_INDEX = 0
    private var pageSize = 3
    private var isLoading = false
    override fun onSetupView() {
        setupRecyclerView()
        callApi()
    }

    private fun setupRecyclerView() {
        if(!::handBookAdapter.isInitialized) handBookAdapter = FullWidthHorizontalAdapter()
        binding.rvHandbook.apply {
            itemAnimator = null
            adapter = handBookAdapter.also {
                it.setOnItemClickListener {
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

    private fun callApi(){
        isLoading = true
        api.getHandBook(DEFAULT_PAGE_INDEX,pageSize).enqueue(object: Callback<ResponseHandBookPageable> {
            override fun onResponse(
                call: Call<ResponseHandBookPageable>,
                response: Response<ResponseHandBookPageable>
            ) {
                if(response.isSuccessful){
                    isLoading = false
                    val listResponse = response.body()!!.content.map { ItemNewInternet(it.id,it.img.toString(),it.title,it.description.toString()) }
                    handBookAdapter.submitList(listResponse)
                }
            }

            override fun onFailure(call: Call<ResponseHandBookPageable>, t: Throwable) {
                isLoading = false
                requireContext().showErrorToast("Oops")
            }

        })

    }
}