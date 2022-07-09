package com.example.healthyapp.ui.fragments

import android.app.ProgressDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.healthyapp.adapter.FullWidthHorizontalAdapter
import com.example.healthyapp.data.AppApi
import com.example.healthyapp.data.response_api_doctor.ResponseApiDoctor
import com.example.healthyapp.data.response_api_hospital.ResponseHospital
import com.example.healthyapp.data.response_api_random_doctor.ResponseDoctor
import com.example.healthyapp.data.response_api_specialist.ResponseSpecialist
import com.example.healthyapp.databinding.FragmentDoctorBinding
import com.example.healthyapp.extensions.setOnClickWithScaleListener
import com.example.healthyapp.extensions.showErrorToast
import com.example.healthyapp.helper.AppUtils
import com.example.healthyapp.helper.RetrofitInstance
import com.example.healthyapp.model.BasicItem
import com.example.healthyapp.model.ItemNewInternet
import com.example.healthyapp.model.post_search_doctor.PostSearchDoctor
import com.example.healthyapp.ui.base.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoctorFragment : BaseFragment<FragmentDoctorBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDoctorBinding
        get() = FragmentDoctorBinding::inflate
    private lateinit var doctorsAdapterFullWidth: FullWidthHorizontalAdapter
    private val api: AppApi by lazy { RetrofitInstance.getInstance()!!.api }
    private val DEFAULT_PAGE_INDEX = 0
    private var pageSize = 3
    private var isLoading = false
    private var specialistId:Any = ""
    private var hospitalId:Any = ""
    private var isClickSearch = false
    private lateinit var postSearchDoctor: PostSearchDoctor
    private lateinit var listHospital:List<BasicItem>
    private lateinit var listSpecialist:List<BasicItem>
    override fun onSetupView() {
        if (!::doctorsAdapterFullWidth.isInitialized) doctorsAdapterFullWidth = FullWidthHorizontalAdapter()
        binding.rvDoctor.apply {
            itemAnimator = null
            adapter = doctorsAdapterFullWidth.also {
                it.setOnItemClickListener {
                    if(it.id!=null)
                        AppUtils.startToViewContentActivity(requireActivity(), it.id!!)
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
            callApi()
        }

        binding.spinnerHospital.apply {
            api.getAllHospitalByStatus(DEFAULT_PAGE_INDEX).enqueue(object:
                Callback<ResponseHospital> {
                override fun onResponse(
                    call: Call<ResponseHospital>,
                    response: Response<ResponseHospital>
                ) {
                    if(response.isSuccessful){
                        listHospital = response.body()!!.content.map { BasicItem(it.id,it.name) }
                        listHospital = listHospital.toMutableList().also { it.add(0, BasicItem(-1,"___Chọn bênh viện___")) }
                        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listHospital.map { it.content }.toTypedArray())
                        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        adapter = spinnerAdapter
                        binding.spinnerHospital.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(
                                p0: AdapterView<*>?,
                                p1: View?,
                                index: Int,
                                p3: Long
                            ) {
                                hospitalId = if(index==0) "" else listHospital[index].id as Int
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseHospital>, t: Throwable) {
                    requireContext().showErrorToast("Oops")
                }
            })

        }

        binding.spinnerSpecialist.apply {
            api.getSpecialist(DEFAULT_PAGE_INDEX,10).enqueue(object:
                Callback<ResponseSpecialist> {
                override fun onResponse(
                    call: Call<ResponseSpecialist>,
                    response: Response<ResponseSpecialist>
                ) {
                    if(response.isSuccessful){
                        listSpecialist = response.body()!!.content.map { BasicItem(it.id,it.name) }
                        listSpecialist = listSpecialist.toMutableList().also { it.add(0, BasicItem(-1,"___Chọn chuyên khoa___")) }
                        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listSpecialist.map { it.content }.toTypedArray())
                        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        adapter = spinnerAdapter
                        binding.spinnerSpecialist.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(
                                p0: AdapterView<*>?,
                                p1: View?,
                                index: Int,
                                p3: Long
                            ) {
                                specialistId = if(index==0) "" else listSpecialist[index].id as Int
                            }

                            override fun onNothingSelected(p0: AdapterView<*>?) {
                            }

                        }
                    }
                }

                override fun onFailure(call: Call<ResponseSpecialist>, t: Throwable) {
                    requireContext().showErrorToast("Oops")
                }
            })
        }

        binding.btnSearch.setOnClickWithScaleListener {
            isClickSearch = true
            callApi()
        }
    }

    private fun callApi(){
        isLoading = true
        val pb = ProgressDialog.show(context,"Đang tải","Chờ chúng tôi lấy dữ liệu")
        pb.setCancelable(false)
        if(!isClickSearch)
        api.getDoctors(DEFAULT_PAGE_INDEX,pageSize).enqueue(object:
            Callback<ResponseApiDoctor> {
            override fun onResponse(
                call: Call<ResponseApiDoctor>,
                response: Response<ResponseApiDoctor>
            ) {
                if(response.isSuccessful){
                    pb.hide()
                    isLoading = false
                    val listResponse = response.body()!!.content.map { ItemNewInternet(it.id,
                        it.img,it.name, it.description
                    ) }
                    doctorsAdapterFullWidth.submitList(listResponse)
                }
            }

            override fun onFailure(call: Call<ResponseApiDoctor>, t: Throwable) {
                pb.hide()
                isLoading = false
                requireContext().showErrorToast("Oops")
            }

        })
        else api.searchDoctors(DEFAULT_PAGE_INDEX,pageSize, PostSearchDoctor(hospitalId,binding.edtSearch.text.toString().trim(),specialistId)).enqueue(object:
                Callback<ResponseDoctor> {
                override fun onResponse(
                    call: Call<ResponseDoctor>,
                    response: Response<ResponseDoctor>
                ) {
                    pb.hide()
                    if(response.isSuccessful){
                        isLoading = false
                        val listResponse = response.body()!!.map { ItemNewInternet(it.id,
                            it.img,it.name, it.description
                        ) }
                        doctorsAdapterFullWidth.submitList(listResponse)
                    }
                }

                override fun onFailure(call: Call<ResponseDoctor>, t: Throwable) {
                    pb.hide()
                    isLoading = false
                    requireContext().showErrorToast(t.message?:"")
                }

            })
    }
}