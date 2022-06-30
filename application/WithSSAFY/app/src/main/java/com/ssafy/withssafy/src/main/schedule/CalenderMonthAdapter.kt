package com.ssafy.withssafy.src.main.schedule

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.ssafy.withssafy.R
import com.ssafy.withssafy.src.viewmodel.ScheduleViewModel
import com.ssafy.withssafy.util.CommonUtils

import java.util.*
import kotlin.collections.ArrayList

class CalenderMonthAdapter(val context: Context,var dates:ArrayList<String>,var scheduleViewModel: ScheduleViewModel,var roomId:Int,val lifecycle:LifecycleOwner,val fragment:Fragment):RecyclerView.Adapter<CalenderMonthAdapter.MonthViewHolder>() {
    val center = Int.MAX_VALUE/2
    private var calender = Calendar.getInstance()

    inner class MonthViewHolder(val layout: View):RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_calender_month,parent,false)
        return MonthViewHolder(view)
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        calender.time = Date()
        calender.set(Calendar.DAY_OF_MONTH,1)
        calender.add(Calendar.MONTH,position-center)
        holder.layout.findViewById<TextView>(R.id.fragment_calender_dateMonth).text = "${CommonUtils.convertEnglishMonth(calender.get(Calendar.MONTH)+1)} ${calender.get(Calendar.YEAR)}"
        val tmpMonth = calender.get(Calendar.MONTH)

        var dayList:MutableList<Date> = MutableList(6*7){Date()}
        for(i in 0..5){
            for(j in 0..6){
                calender.add(Calendar.DAY_OF_MONTH,(1-calender.get(Calendar.DAY_OF_WEEK))+j)
                dayList[i*7+j] = calender.time
            }
            calender.add(Calendar.WEEK_OF_MONTH,1)
        }

        val dayListManager = GridLayoutManager(holder.layout.context,7)
        val dayListAdapter = CalenderDayAdapter(tmpMonth,dayList,dates,scheduleViewModel,context,roomId,lifecycle, fragment)

        holder.layout.findViewById<RecyclerView>(R.id.fragment_calender_dayRv).apply {
            layoutManager = dayListManager
            adapter = dayListAdapter
        }

    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

}