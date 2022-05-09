package com.ssafy.withssafy.src.main.schedule

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.github.tlaabs.timetableview.Schedule
import com.github.tlaabs.timetableview.TimetableView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.databinding.ItemClassCurriculrumBinding
import com.ssafy.withssafy.src.dto.WeekSchedule
import com.ssafy.withssafy.src.network.service.ScheduleService
import com.ssafy.withssafy.src.viewmodel.ScheduleViewModel
import kotlinx.coroutines.runBlocking
import retrofit2.Response

class ClassCurrculAdapter(val scheduleViewModel: ScheduleViewModel, val context:Context) : RecyclerView.Adapter<ClassCurrculAdapter.ClassViewHolder>() {
    var scheduleList = mutableListOf<WeekSchedule>()
    var scheduleDtoList = mutableListOf<com.ssafy.withssafy.src.dto.Schedule>()
    inner class ClassViewHolder(private val binding:ItemClassCurriculrumBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(schedule:WeekSchedule){
            binding.schedule = schedule
            var timetable = itemView.findViewById<TimetableView>(R.id.timetable)
            timetable.add(schedule.schedules)

            var json = timetable.createSaveData()
            timetable.load(json)

            timetable.setOnStickerSelectEventListener { idx, schedules ->
                showOptionDialog(schedules[idx].classTitle,scheduleViewModel.liveScheduleIndex.value!!.get(idx))
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        return ClassViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_class_curriculrum,parent,false))
    }

    override fun onBindViewHolder(holder: ClassViewHolder, position: Int) {
        holder.apply {
            bind(scheduleList[position])
        }
    }

    override fun getItemCount(): Int {
        return scheduleList.size
    }
    private fun showOptionDialog(title:String, id:Int){
        var dialog = Dialog(context)
        var dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_class_curriculum,null)
        if(dialogView.parent!=null){
            (dialogView.parent as ViewGroup).removeView(dialogView)
        }
        dialog.setContentView(dialogView)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogView.findViewById<TextView>(R.id.fragment_classcurrcul_dialog_title).text = title
        dialog.show()

        dialogView.findViewById<AppCompatButton>(R.id.fragment_classcurrcul_dialog_modify).setOnClickListener {
            modifyClickListener.onClick(id)
//
            dialog.dismiss()
        }
        dialogView.findViewById<AppCompatButton>(R.id.fragment_classcurrcul_dialog_delete).setOnClickListener {
            
            val response : Response<Any?>
            runBlocking {
                response = ScheduleService().deleteSchedule(id)
            }
            if(response.code() == 204){
                Log.d("TAG", "showOptionDialog: ")
            }
        }
        dialogView.findViewById<ImageButton>(R.id.fragment_classcurrcul_dialog_cancle).setOnClickListener {
            dialog.dismiss()
        }
    }
    interface ModifyClickListener{
        fun onClick(scheduleId:Int)
    }
    private lateinit var modifyClickListener : ModifyClickListener
    fun setModifyItemClickListener(modifyClickListener: ModifyClickListener){
        this.modifyClickListener = modifyClickListener
    }
}