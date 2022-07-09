package com.example.healthyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.healthyapp.data.response_api_doctor.Content
import com.example.healthyapp.databinding.ItemDoctorBinding


class DoctorsAdapter : RecyclerView.Adapter<DoctorsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemDoctorBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: Content){
            binding.apply {
                Glide.with(root.context).asBitmap().centerCrop().load(item.img).into(imgAvatar)
                tvName.text = "Bs." +item.name
                tvSpecialist.text = "Chuyên khoa: " +item.specializedName
                tvHospital.text = "Bệnh viện: " +item.hospitalName
                tvLocationHospital.text = "Địa chỉ: " +item.hospitalLocation
            }
        }
    }

    private val diffUtilItemCallBack = object:DiffUtil.ItemCallback<Content>(){

        override fun areItemsTheSame(oldItem: Content, newItem: Content): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Content, newItem: Content): Boolean {
            return oldItem.id == newItem.id
        }

    }

    private val differ = AsyncListDiffer(this,diffUtilItemCallBack)

    fun submitList(list:List<Content>){
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemDoctorBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    fun getItemAtPosition(position:Int) = differ.currentList[position]

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItemAtPosition(position)
        holder.bind(item)
        holder.binding.root.setOnClickListener {
            onClickItemListener?.let { it(item) }
        }
    }

    private var onClickItemListener:((Content)->Unit)?=null

    fun setOnItemClickListener(listener:(Content)->Unit){
        onClickItemListener= listener
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}