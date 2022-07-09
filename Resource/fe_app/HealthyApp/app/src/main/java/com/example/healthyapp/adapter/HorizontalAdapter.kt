package com.example.healthyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.healthyapp.databinding.ItemImageTextHorizontalBinding
import com.example.healthyapp.extensions.toVisible
import com.example.healthyapp.model.ItemNewInternet

class HorizontalAdapter() : RecyclerView.Adapter<HorizontalAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemImageTextHorizontalBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: ItemNewInternet){
            binding.apply {
                Glide.with(binding.root.context).asBitmap().fitCenter().load(item.image).into(binding.imgTitle)
                tvNameDoctor.text = item.title
                item.des.let {
                    tvDescription.toVisible()
                    tvDescription.text = HtmlCompat.fromHtml(item.des,HtmlCompat.FROM_HTML_MODE_LEGACY).toString().replace("<[^>]*>".toRegex(),"").replace("\n","")
                }
            }
        }
    }

    private val diffUtilItemCallBack = object: DiffUtil.ItemCallback<ItemNewInternet>(){

        override fun areItemsTheSame(oldItem: ItemNewInternet, newItem: ItemNewInternet): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: ItemNewInternet, newItem: ItemNewInternet): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private val differ = AsyncListDiffer(this,diffUtilItemCallBack)

    fun submitList(list:List<ItemNewInternet>){
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalAdapter.ViewHolder {
        return ViewHolder(ItemImageTextHorizontalBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    private fun getItemAtPosition(position:Int) = differ.currentList[position]

    override fun onBindViewHolder(holder: HorizontalAdapter.ViewHolder, position: Int) {
        val item = getItemAtPosition(position)
            holder.apply {
                bind(item)
                binding.root.setOnClickListener {
                    onClickItemListener?.let { it(item) }
                }
            }
    }

    private var onClickItemListener:((ItemNewInternet)->Unit)?=null
    fun setOnItemClickListener(listener:(ItemNewInternet)->Unit){
        onClickItemListener= listener
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}