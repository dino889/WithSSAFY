package com.ssafy.withssafy.src.main.schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.databinding.ItemScheduleBinding
import com.ssafy.withssafy.src.dto.Schedule

class ClassCurrculWriteAdapter() : RecyclerView.Adapter<ClassCurrculWriteAdapter.WriteViewHolder>() {
   var list = mutableListOf<Schedule>()
    var checkList = mutableListOf<Int>()
    var checked = false
    inner class WriteViewHolder(private val binding:ItemScheduleBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(schedule:Schedule){
            binding.schedule = schedule
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WriteViewHolder {
        return WriteViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_schedule,parent,false))
    }

    override fun onBindViewHolder(holder: WriteViewHolder, position: Int) {
        holder.apply {
            bind(list[position])
            if(checked == true){
                itemView.findViewById<CheckBox>(R.id.fragment_schedule_check).visibility = View.VISIBLE
            }else{
                itemView.findViewById<CheckBox>(R.id.fragment_schedule_check).visibility = View.GONE
            }
            if(itemView.findViewById<CheckBox>(R.id.fragment_schedule_check).isChecked){
                checkList.add(layoutPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun updateCheckBox(check:Boolean){
        checked = check
    }
}