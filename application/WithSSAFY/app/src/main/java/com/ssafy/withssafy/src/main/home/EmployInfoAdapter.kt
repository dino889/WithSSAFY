package com.ssafy.withssafy.src.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.databinding.RecyclerviewEmployItemBinding
import com.ssafy.withssafy.src.dto.Recruit

class EmployInfoAdapter() : RecyclerView.Adapter<EmployInfoAdapter.EmployInfoHolder>() {
    var list = mutableListOf<Recruit>()
    inner class EmployInfoHolder(private val binding: RecyclerviewEmployItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recruit : Recruit) {
            binding.recruit = recruit
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployInfoHolder {
        return EmployInfoHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.recyclerview_employ_item,parent,false))
    }

    override fun onBindViewHolder(holder: EmployInfoHolder, position: Int) {
        holder.apply {
            bind(list[position])
            itemView.setOnClickListener {
                itemClickListener.onClick(it,position, list[position].id!!)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private lateinit var itemClickListener : ItemClickListener
    interface ItemClickListener{
        fun onClick(view: View, position: Int, id: Int)
    }
    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

}