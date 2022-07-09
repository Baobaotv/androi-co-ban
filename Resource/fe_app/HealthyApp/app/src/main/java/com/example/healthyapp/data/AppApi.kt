package com.example.healthyapp.data

import com.example.healthyapp.data.response_api_doctor.Content
import com.example.healthyapp.data.response_api_doctor.ResponseApiDoctor
import com.example.healthyapp.data.response_api_random_doctor.ResponseDoctor
import com.example.healthyapp.data.response_api_handbook.ResponseHandBook
import com.example.healthyapp.data.response_api_handbook_pageable.ResponseHandBookPageable
import com.example.healthyapp.data.response_api_hospital.ResponseHospital
import com.example.healthyapp.data.response_api_profile.ResponseProfile
import com.example.healthyapp.data.response_api_sign_in.ResponseSignIn
import com.example.healthyapp.data.response_api_specialist.ResponseSpecialist
import com.example.healthyapp.data.response_api_specialist_random.ResponseRandomSpecialist
import com.example.healthyapp.data.response_detail_handbook.ResponseDetailHandBook
import com.example.healthyapp.data.response_detail_hospital.ResponseDetailHospital
import com.example.healthyapp.data.response_detail_user_booking.ResponseUserBooking
import com.example.healthyapp.model.post_booking.PostBodyBooking
import com.example.healthyapp.model.post_search_doctor.PostSearchDoctor
import com.example.healthyapp.model.post_sign_in.PostBodySignIn
import com.example.healthyapp.model.post_signup.PostBodySignUp
import com.example.healthyapp.model.post_update_profile.PostUpdateProfile
import retrofit2.Call
import retrofit2.http.*

interface AppApi {
    @GET("api/specicalized/get-random")
    @Headers("accept:*/*")
    fun getRandomSpecialist():Call<ResponseRandomSpecialist>

    @GET("api/specicalized")
    @Headers("accept:*/*")
    fun getSpecialist(@Query("page") page:Int, @Query("size") size:Int):Call<ResponseSpecialist>

    @GET("api/user/get-random-docter")
    @Headers("accept:*/*")
    fun getRandomDoctor():Call<ResponseDoctor>

    @GET("api/user/docter")
    @Headers("accept:*/*")
    fun getDoctors(@Query("page") page:Int, @Query("size") size:Int):Call<ResponseApiDoctor>

    @POST("api/user/search-docter")
    @Headers("accept:*/*")
    fun searchDoctors(@Query("page") page:Int, @Query("size") size:Int,@Body postBoSearchDoctor: PostSearchDoctor):Call<ResponseDoctor>

    @GET("api/user/specialzed/{id}")
    @Headers("accept:*/*")
    fun getDoctorsInSpecialist(@Path("id") id:Int,@Query("page") page:Int, @Query("size") size:Int):Call<ResponseApiDoctor>

    @GET("api/user/hospital/{id}")
    @Headers("accept:*/*")
    fun getDoctorsInHospital(@Path("id") id:Int,@Query("page") page:Int, @Query("size") size:Int):Call<ResponseApiDoctor>

    @GET("api/user/docter/{id}")
    @Headers("accept:*/*")
    fun getDoctorById(@Path("id") id:Int):Call<Content>

    @GET("api/handbook/get-random")
    @Headers("accept:*/*")
    fun getRandomHandBook():Call<ResponseHandBook>

    @GET("api/handbook")
    @Headers("accept:*/*")
    fun getHandBook(@Query("page") page:Int, @Query("size") size:Int):Call<ResponseHandBookPageable>

    @GET("api/handbook/{id}")
    @Headers("accept:*/*")
    fun getHandBookDetailById(@Path("id") id:Int):Call<ResponseDetailHandBook>

    @GET("api/hospital/get-random")
    @Headers("accept:*/*")
    fun getRandomHospital():Call<ResponseRandomSpecialist>

    @GET("api/hospital")
    @Headers("accept:*/*")
    fun getHospital(@Query("page") page:Int, @Query("size") size:Int):Call<ResponseHospital>

    @GET("api/hospital/get-all-by-status")
    @Headers("accept:*/*")
    fun getAllHospitalByStatus(@Query("page") page:Int, @Query("size") size:Int=11):Call<ResponseHospital>

    @GET("api/hospital/{id}")
    @Headers("accept:*/*")
    fun getHospitalDetailById(@Path("id") id:Int):Call<ResponseDetailHospital>

    @POST("api/signup")
    @Headers("accept:*/*")
    fun signup(@Body postBodySignUp: PostBodySignUp):Call<Any>

    @POST("api/signin")
    @Headers("accept:*/*")
    fun signin(@Body postBodySignIn: PostBodySignIn):Call<ResponseSignIn>

    @GET("api/media/check/{idDoctor}/{idWorktime}/{date}")
    @Headers("accept:*/*")
    fun checkTimeBooking(@Header("Authorization") token:String, @Path("idDoctor") idDoctor:Int, @Path("idWorktime") idWorkTime:Int, @Path("date") date:String):Call<Boolean>

    @GET("api/media/get-by-current-login")
    @Headers("accept:*/*")
    fun getBookingDetail(@Header("Authorization") token:String,):Call<ResponseUserBooking>

    @POST("api/booking")
    @Headers("accept:*/*")
    fun booking(@Header("Authorization") token:String, @Body postBodyBooking: PostBodyBooking):Call<Boolean>

    @GET("api/current-login")
    @Headers("accept:*/*")
    fun getProfile(@Header("Authorization") token:String):Call<ResponseProfile>

    @PUT("/api/updateClient")
    @Headers("accept:*/*")
    fun updateClient(@Header("Authorization") token:String,@Body postUpdateProfile: PostUpdateProfile):Call<Boolean>
}