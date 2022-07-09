package com.example.healthyapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.healthyapp.data.response_detail_user_booking.ResponseUserBookingItem
import com.example.healthyapp.databinding.ItemDetailUserBookingBinding
import com.example.healthyapp.extensions.toGone
import com.example.healthyapp.extensions.toVisible


class BookingDetailAdapter(private val onChangeDateBookingListener: ((Int) -> Unit?)? =null) : RecyclerView.Adapter<BookingDetailAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemDetailUserBookingBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: ResponseUserBookingItem){
            binding.apply {
                tvDateBooking.text = "Ngày khám : ${item.date}"
                tvAddress.text = "Địa chỉ bệnh nhân : ${item.location}"
                tvDoctor.text = "Bác sĩ : ${item.doctor.name}"
                tvGender.text = "Giới tính : ${item.sex}"
                tvHospital.text = "Bệnh viện : ${item.hospitalName}"
                tvLocationHospital.text = "Địa chỉ bệnh viện: ${item.doctor.hospitalLocation}"
                tvNamePatient.text = "Tên bệnh nhân : ${item.namePatient}"
                tvPhoneNumbder.text = "Số điện thoại : ${item.doctor.phone}"
                tvReason.text = "Lý do : ${item.reason}"
                tvTime.text = "Thời gian : ${if(item.wordTimeTime.isNullOrEmpty()) "Không có dữ liệu" else item.wordTimeTime}"
                tvTypeBooking.text = "Hình thức khám : ${item.type}"
            }
        }
    }

    private val diffUtilItemCallBack = object:DiffUtil.ItemCallback<ResponseUserBookingItem>(){

        override fun areItemsTheSame(oldItem: ResponseUserBookingItem, newItem: ResponseUserBookingItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ResponseUserBookingItem, newItem: ResponseUserBookingItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private val differ = AsyncListDiffer(this,diffUtilItemCallBack)

    fun submitList(list:List<ResponseUserBookingItem>){
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemDetailUserBookingBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    private fun getItemAtPosition(position:Int) = differ.currentList[position]

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItemAtPosition(position)
        holder.bind(item)
        holder.binding.tvDateBooking.setOnClickListener {
            if(holder.binding.lyDetail.visibility ==View.GONE) holder.binding.lyDetail.toVisible() else holder.binding.lyDetail.toGone()
        }
//        holder.binding.tvChangeDate.setOnClickListener {
//            if(item.doctor.id!=-1) onChangeDateBookingListener?.let { callback -> callback(item.doctor.id) }
//        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}