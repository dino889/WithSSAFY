package com.ssafy.withssafy.src.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.databinding.ItemRequestHomeItemBinding
import com.ssafy.withssafy.databinding.RecyclerviewEmployItemBinding
import com.ssafy.withssafy.src.dto.Recruit
import com.ssafy.withssafy.src.dto.User
import com.ssafy.withssafy.src.main.board.JobAdapter

class RequestAdapter(var isHome: Boolean) : RecyclerView.Adapter<RequestAdapter.RequestHolder>() {
    var list = mutableListOf<User>()
    inner class RequestHolder(private val binding: ItemRequestHomeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var localTv = binding.fragmentHomeRequestLocal
        fun bind(recruit : User) {
            binding.user = recruit
            binding.executePendingBindings()

            if(recruit.classRoomId >= 6 && recruit.classRoomId <= 8) {
                localTv.text = "구미지역"
            } else if(recruit.classRoomId >= 10 && recruit.classRoomId <= 14) {
                localTv.text = "서울지역"
            } else if(recruit.classRoomId >= 15 && recruit.classRoomId <= 16) {
                localTv.text = "대전지역"
            } else if(recruit.classRoomId >= 17 && recruit.classRoomId <= 18) {
                localTv.text = "광주지역"
            } else if(recruit.classRoomId >= 19 && recruit.classRoomId <= 20) {
                localTv.text = "부울경지역"
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestHolder {
        return RequestHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_request_home_item,parent,false))
    }

    override fun onBindViewHolder(holder: RequestHolder, position: Int) {
        holder.apply {
            bind(list[position])

            itemView.setOnClickListener {
                itemClickListener.onClick(it,position,list[position].id)
            }
        }
    }

    override fun getItemCount(): Int {
        if(list.size > 5 && isHome) {
            return 5
        } else {
            return list.size
        }
    }

    interface ItemClickListener{
        fun onClick(view: View, position: Int, id: Int)
    }

    private lateinit var itemClickListener : RequestAdapter.ItemClickListener
    fun setItemClickListener(itemClickListener: RequestAdapter.ItemClickListener){
        this.itemClickListener = itemClickListener
    }


}