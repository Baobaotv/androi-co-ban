package com.example.healthyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.healthyapp.databinding.ItemImageTextHorizontalBinding
import com.example.healthyapp.databinding.ItemImageTextHorizontalFullWidthBinding
import com.example.healthyapp.extensions.toGone
import com.example.healthyapp.extensions.toInvisible
import com.example.healthyapp.extensions.toVisible
import com.example.healthyapp.model.ItemNewInternet

class FullWidthHorizontalAdapter() : RecyclerView.Adapter<FullWidthHorizontalAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemImageTextHorizontalFullWidthBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: ItemNewInternet){
            binding.apply {
                Glide.with(binding.root.context).asBitmap().fitCenter().load(item.image).into(binding.imgTitle)
                tvNameDoctor.text = if(item.title=="null") "Không tiêu đề" else item.title
                item.des.let {
                    tvDescription.toVisible()
                    tvDescription.text = HtmlCompat.fromHtml(item.des,HtmlCompat.FROM_HTML_MODE_LEGACY).toString().replace("<[^>]*>".toRegex(),"").replace("\n","")
                    if(tvDescription.text.trim()=="null") tvDescription.text = "Không tiêu đề"
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FullWidthHorizontalAdapter.ViewHolder {
        return ViewHolder(ItemImageTextHorizontalFullWidthBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    private fun getItemAtPosition(position:Int) = differ.currentList[position]

    override fun onBindViewHolder(holder: FullWidthHorizontalAdapter.ViewHolder, position: Int) {
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