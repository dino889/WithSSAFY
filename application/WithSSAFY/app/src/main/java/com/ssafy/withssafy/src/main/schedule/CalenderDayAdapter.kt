package com.ssafy.withssafy.src.main.schedule

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.src.dto.Schedule
import com.ssafy.withssafy.src.viewmodel.ScheduleViewModel
import com.ssafy.withssafy.util.CommonUtils
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "CalenderDayAdapter"
class CalenderDayAdapter(val tmpMonth:Int, val dayList:MutableList<Date>,val dates:ArrayList<String>, val scheduleViewModel: ScheduleViewModel, val context:Context, var roomId:Int, var lifecycle:LifecycleOwner, var fragment: Fragment):
    RecyclerView.Adapter<CalenderDayAdapter.DayViewHolder>() {
    val ROW = 6

    inner class DayViewHolder(val layout: View):RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_calender_day,parent,false)
        return DayViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        var day = holder.layout.findViewById<TextView>(R.id.fragment_calender_dayTv)
        var clicked = false
        day.text = dayList[position].date.toString()
        day.setTextColor(when(position%7){
            0 -> Color.RED
            6-> Color.BLUE
            else -> Color.BLACK
        })

        if(tmpMonth != dayList[position].month){
            day.alpha = 0.4f
        }

        for(i in 0..dates.size-1) {
            var item = dates[i]
            var year = item.substring(0,4)
            var month = item.substring(5,7)
            var days = item.substring(8,10)
            var time = item.substring(item.length-8,item.length-3)

            var originMonth = (dayList[position].month+1).toString()
            var originDay = day.text.toString()

            if(dayList[position].month.toString().length == 1){
                originMonth = "0${originMonth}"
            }
            if(day.text.toString().length == 1){
                originDay = "0${originDay}"
            }
            
            var originDates = "${originMonth}-${originDay}"
            var dates = "${month}-${days}"

            var tmp = day.text.toString()
            if(tmp.length == 1){
                tmp = "0${tmp}"
            }
            var fullDate = "${year}-${dates}"
            var localFullDate = LocalDate.of(year.toInt(),month.toInt(),days.toInt())


            var week = localFullDate.dayOfWeek.getDisplayName(TextStyle.FULL,Locale.US)
            if(tmp.equals(originDay)){
                if(originDates.equals(dates)){
                    holder.itemView.findViewById<ImageView>(R.id.fragment_calendar_point).visibility = View.VISIBLE
                    holder.layout.setOnClickListener {
                        runBlocking {
                            scheduleViewModel.getGenarateScheduleDetails("${year}-${dates}",roomId)
                        }
                        showDialogSchedule(dates,week)
                        Log.d(TAG, "onBindViewHolder: $originDates  $dates")
                    }
                }
            }
        }
    }
    override fun getItemCount(): Int {
        return ROW*7
    }
    interface ItemClickListener{
        fun onClick(view: View, position: Int, day: String, week:String)
    }

    private lateinit var itemClickListener : ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }
    private fun showDialogSchedule(dates :String, week:String){
        var dialog = Dialog(context)
        var dialogView = LayoutInflater.from(context).inflate(R.layout.fragment_calender_day_dialog,null)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(dialogView)

        val param = WindowManager.LayoutParams()
        param.width = WindowManager.LayoutParams.MATCH_PARENT
        param.height = WindowManager.LayoutParams.MATCH_PARENT
        val window = dialog.window
        window?.attributes = param
        dialog.show()
        dialogView.findViewById<ImageButton>(R.id.fragment_calender_dialog_cancle).setOnClickListener {
            dialog.dismiss()
        }
        dialogView.findViewById<TextView>(R.id.fragment_calender_dialog_week).setText(week)
        var tmpDates = "${dates.substring(0,2)}월 ${dates.substring(3,5)}일"
        dialogView.findViewById<TextView>(R.id.fragment_calender_dialog_date).setText(tmpDates)

        var detailAdapter = DayDetailAdapter()
        var dialogRv = dialogView.findViewById<RecyclerView>(R.id.fragment_calender_dialog_rv)

        scheduleViewModel.genarateScheduleDetails.observe(lifecycle){
            detailAdapter.list = it
        }
        dialogRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = detailAdapter
        }
        detailAdapter.setItemClickListener(object : DayDetailAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int, scheduleId: Int) {
                var scheduleId = bundleOf("scheduleId" to scheduleId)
                fragment.findNavController().navigate(R.id.curriculumWriteFragment, scheduleId)
                dialog.dismiss()
            }
        })
    }
}