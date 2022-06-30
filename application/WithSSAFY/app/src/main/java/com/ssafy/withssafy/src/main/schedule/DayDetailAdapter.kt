package com.ssafy.withssafy.src.main.schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.databinding.ItemCalendarDetailBinding
import com.ssafy.withssafy.src.dto.Schedule

class DayDetailAdapter() : RecyclerView.Adapter<DayDetailAdapter.DayViewHolder>() {
    var list = mutableListOf<Schedule>()
    var userId = ApplicationClass.sharedPreferencesUtil.getUser().id
    var studentId = ApplicationClass.sharedPreferencesUtil.getUser().studentId
    inner class DayViewHolder(private val binding:ItemCalendarDetailBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(schedule:Schedule){
            binding.schedule = schedule
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        return DayViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_calendar_detail,parent,false))
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.apply {
            bind(list[position])
            if(studentId == null){
                //admin
                itemView.setOnClickListener {
                    itemClickListener.onClick(it,position,list[position].id)
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface ItemClickListener{
        fun onClick(view: View, position: Int, scheduleId:Int)
    }

    private lateinit var itemClickListener : ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }
}