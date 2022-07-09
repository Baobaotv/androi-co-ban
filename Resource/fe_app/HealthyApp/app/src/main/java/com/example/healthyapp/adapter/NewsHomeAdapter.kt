package com.example.healthyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.healthyapp.databinding.ItemNewsBinding
import com.example.healthyapp.model.ItemNew


class NewsHomeAdapter : RecyclerView.Adapter<NewsHomeAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemNewsBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: ItemNew){
            binding.apply {
                imgTitle.setImageResource(item.image)
                tvDescription.text = item.title
            }
        }
    }

    private val diffUtilItemCallBack = object:DiffUtil.ItemCallback<ItemNew>(){

        override fun areItemsTheSame(oldItem: ItemNew, newItem: ItemNew): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: ItemNew, newItem: ItemNew): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private val differ = AsyncListDiffer(this,diffUtilItemCallBack)

    fun submitList(list:List<ItemNew>){
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    private fun getItemAtPosition(position:Int) = differ.currentList[position]

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItemAtPosition(position)
        holder.bind(item)
        holder.binding.root.setOnClickListener {
            onClickItemListener?.let { it(item) }
        }
    }

    private var onClickItemListener:((ItemNew)->Unit)?=null

    fun setOnItemClickListener(listener:(ItemNew)->Unit){
        onClickItemListener= listener
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}