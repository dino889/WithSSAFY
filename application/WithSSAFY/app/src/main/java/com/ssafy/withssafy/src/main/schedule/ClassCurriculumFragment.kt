package com.ssafy.withssafy.src.main.schedule

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.ColorStateListDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.os.bundleOf
import androidx.core.view.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.github.tlaabs.timetableview.Sticker
import com.github.tlaabs.timetableview.Time
import com.github.tlaabs.timetableview.TimetableView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentClassCurriculumBinding
import com.ssafy.withssafy.src.dto.Schedule
import com.ssafy.withssafy.src.dto.WeekSchedule
import com.ssafy.withssafy.src.network.service.ScheduleService
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val TAG = "ClassCurriculumFragment"
class ClassCurriculumFragment : BaseFragment<FragmentClassCurriculumBinding>(FragmentClassCurriculumBinding::bind,R.layout.fragment_class_curriculum) {
    private lateinit var timetable:TimetableView
    val studentId = ApplicationClass.sharedPreferencesUtil.getUser().studentId
    val userId = ApplicationClass.sharedPreferencesUtil.getUser().id
    private var isStudent = false
    private lateinit var classAdapter : ClassCurrculAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        runBlocking {
            userViewModel.getUser(ApplicationClass.sharedPreferencesUtil.getUser().id, 1)
            scheduleViewModel.getClassSchedule(userViewModel.loginUserInfo.value!!.classRoomId)
            scheduleViewModel.getAllClassSchedules(userViewModel.loginUserInfo.value!!.classRoomId)
        }

        setListener()
    }

    private fun setListener() {
        initTimeTable()
        isStudent = studentId != null
    }
    @SuppressLint("ResourceAsColor")
    private fun initTimeTable(){
        classAdapter = ClassCurrculAdapter(scheduleViewModel, requireContext())

        val weeksSchedules : MutableList<WeekSchedule> = mutableListOf()
        if(scheduleViewModel.allClassSchedules.value!!.size == 0){
            weeksSchedules.add(WeekSchedule(0, arrayListOf(), mutableListOf()))
            classAdapter.scheduleList = weeksSchedules
        }else{
            scheduleViewModel.allClassSchedules.observe(viewLifecycleOwner) {
                var schedules : ArrayList<com.github.tlaabs.timetableview.Schedule> = arrayListOf()
                var scheduleDtos : MutableList<Schedule> = mutableListOf()
                var weeks = it[0].weeks

                var idx = 0;
                for(item in it){
                    var startTime = item.startDate.substring(item.startDate.length-8,item.startDate.length)
                    var startTimeHour = startTime.substring(0,2)
                    var startTimeMinute = startTime.substring(3,5)
                    var endTime = item.endDate.substring(item.endDate.length-8,item.endDate.length)
                    var endTimeHour = endTime.substring(0,2)
                    var endTimeMinute = endTime.substring(3,5)
                    var schedule = com.github.tlaabs.timetableview.Schedule()

                    schedule.startTime = Time(startTimeHour.toInt(), startTimeMinute.toInt())
                    schedule.endTime = Time(endTimeHour.toInt(), endTimeMinute.toInt())
                    schedule.day = findWeeks(item.startDate.substring(0,10))-1
                    schedule.classTitle = item.title
                    schedule.classPlace= item.memo
                    schedule.professorName = ""

                    idx++

                    if(weeks != item.weeks){
                        weeksSchedules.add(WeekSchedule(weeks, schedules,scheduleDtos))
                        weeks = item.weeks
                        schedules = arrayListOf()
                        scheduleDtos = mutableListOf()
                    }

                    schedules.add(schedule)
                    scheduleDtos.add(item)
                    scheduleViewModel.insertScheduleIndex(item.id)

                    if(idx == it.size){
                        weeksSchedules.add(WeekSchedule(weeks,schedules,scheduleDtos))
                    }
                }
                classAdapter.scheduleList = weeksSchedules
                binding.fragmentClassCurrculRv.scrollToPosition(weeksSchedules.size-1)
            }
        }


        binding.fragmentClassCurrculRv.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter = classAdapter

            adapter!!.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }

        classAdapter.setModifyItemClickListener(object: ClassCurrculAdapter.ModifyClickListener {
            override fun onClick(scheduleId: Int) {
                Log.d(TAG, "onClick: ${scheduleId}")
//                var scheduleIds = bundleOf("scheduleId" to scheduleId)
//                this@ClassCurriculumFragment.findNavController().navigate(R.id.curriculumWriteFragment, scheduleIds)
            }
        })
    }
    private fun findWeeks(date:String):Int{
        var dateTime = LocalDate.parse(date)
        return dateTime.dayOfWeek.value!!
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClassCurriculumFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}