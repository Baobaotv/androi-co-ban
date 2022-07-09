package com.example.healthyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.healthyapp.data.response_api_doctor.LstWorkTime
import com.example.healthyapp.databinding.ItemWorkTimeBinding


class WorkTimeAdapter : RecyclerView.Adapter<WorkTimeAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemWorkTimeBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: LstWorkTime){
           binding.apply {
               tvTitleTime.text = item.name
               tvTime.text = item.time
           }
        }
    }

    private val diffUtilItemCallBack = object:DiffUtil.ItemCallback<LstWorkTime>(){

        override fun areItemsTheSame(oldItem: LstWorkTime, newItem: LstWorkTime): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: LstWorkTime, newItem: LstWorkTime): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private val differ = AsyncListDiffer(this,diffUtilItemCallBack)

    fun submitList(list:List<LstWorkTime>){
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemWorkTimeBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    private fun getItemAtPosition(position:Int) = differ.currentList[position]

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItemAtPosition(position)
        holder.bind(item)
        holder.binding.root.setOnClickListener {
            onClickItemListener?.let { it(item) }
        }
    }

    private var onClickItemListener:((LstWorkTime)->Unit)?=null

    fun setOnItemClickListener(listener:(LstWorkTime)->Unit){
        onClickItemListener= listener
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}