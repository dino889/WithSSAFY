package com.ssafy.withssafy.src.main.schedule

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentCurriculumWriteBinding
import com.ssafy.withssafy.src.dto.ClassRoom
import com.ssafy.withssafy.src.dto.Schedule
import com.ssafy.withssafy.src.network.service.ScheduleService
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import java.util.*

private const val TAG = "CurriculumWriteFragment"
class CurriculumWriteFragment : BaseFragment<FragmentCurriculumWriteBinding>(FragmentCurriculumWriteBinding::bind,R.layout.fragment_curriculum_write) {
    private var type = -1
    private var titleType = "";
    private var week = 0
    private var roomId = 0

    private var scheduleId = 0
    var title = arrayListOf<String>("오전미팅","프로젝트진행","오후미팅","종료미팅","중식","라이브방송","직접입력")

    private lateinit var writeAdapter:ClassCurrculWriteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            scheduleId = it.getInt("scheduleId")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        runBlocking {
            userViewModel.getUser(ApplicationClass.sharedPreferencesUtil.getUser().id,1)
            userViewModel.getClassRoomList()
        }

        setListener()
    }
    private fun setListener(){
        initCheckBox()
        initSpinner()
        initDatePicker()
        initTimePicker()
        initButtons()
        if(scheduleId > 0){
            initData()
            deleteButtonClick()
        }
        initAdapter()
    }
    private fun deleteButtonClick(){
        binding.scheduleDeleteButn.setOnClickListener {
            val response:Response<Any?>
            runBlocking {
                response = ScheduleService().deleteSchedule(scheduleId)
            }
            if(response.code() == 204){
                if(response.isSuccessful){
                    showCustomToast("삭제되었습니다.")
                    this@CurriculumWriteFragment.findNavController().navigate(R.id.scheduleFragment)
                }
            }

        }
    }
    private fun initData(){
        runBlocking {
            scheduleViewModel.getScheduleById(scheduleId)
        }
        var schedule = scheduleViewModel.schedule.value!!
        binding.scheduleDeleteButn.visibility = View.VISIBLE

        Log.d(TAG, "initData: ${schedule.classRoomId}")
        if( schedule.classRoomId >= 272 ){
            binding.fragmentCurrculWriteTypeClass.isChecked = false
            binding.fragmentCurrculWriteTypeFull.isChecked = true
            binding.fragmentCurrculWriteWeeksSpinner.visibility = View.GONE
            binding.roomIdSpinnerLayout.visibility = View.VISIBLE
        }else{
            binding.fragmentCurrculWriteTypeClass.isChecked = true
            binding.fragmentCurrculWriteTypeFull.isChecked = false
        }

        binding.fragmentCurrculWriteWeeksSpinner.setSelection(schedule.weeks-1)
        binding.fragmentCurrWriteMemoEdit.setText(schedule.memo)
        var flag = false
        for(i in 0..title.size-1){
            if(title[i].equals(schedule.title)){
                binding.fragmentCurrculWriteTitleSpinner.setSelection(i)
                flag = true
            }
        }
        if(!flag){
            binding.fragmentCurrculWriteTitleSpinner.setSelection(6)
            binding.fragmentCurrculWriteTitleEdit.visibility = View.VISIBLE
            binding.fragmentCurrculWriteTitleEdit.setText(schedule.title)
        }
        var rooms = userViewModel.classRommList.value!!
        var tmp = arrayListOf<ClassRoom>()
        for(item in rooms){
            if(item.classDescription.equals("전체")){
                tmp.add(item)
            }
        }
        for(i in 0..tmp.size-1){
            if(tmp[i].id == schedule.classRoomId){
                binding.roomListSpinner.setSelection(i)
            }
        }


//        binding.fragmentCurrculWriteTitleSpinner.setSelection(6)
        var startDate = schedule.startDate.substring(0,10)
        binding.fragmentCurrculWriteStartDateTv.text = startDate
        var startTime = schedule.startDate.substring(schedule.startDate.length-8,schedule.startDate.length-3)
        Log.d(TAG, "initData: $startTime")
        if(startTime.substring(0,2).toInt() > 12){
            binding.fragmentCurrculWriteStartTimeTv.text = "AM ${startTime}"
        }else{
            binding.fragmentCurrculWriteStartTimeTv.text = "PM ${startTime}"
        }

        var endDate = schedule.endDate.substring(0,10)
        binding.fragmentCurrculWriteEndDateTv.text = endDate
        var endTime = schedule.endDate.substring(schedule.endDate.length-8,schedule.endDate.length-3)
        Log.d(TAG, "initData: $endTime")
        if(endTime.substring(0,2).toInt() > 12){
            binding.fragmentCurrculWriteEndTimeTv.text = "AM ${endTime}"
        }else{
            binding.fragmentCurrculWriteEndTimeTv.text = "PM ${endTime}"
        }

        binding.fragmentCurrculWriteInsert.text = "수정"
        binding.fragmentCurrculWriteAddBucketBtn.visibility = View.GONE
        binding.fragmentCurrculWriteBucketRv.visibility = View.GONE
    }
    private fun initAdapter(){
        writeAdapter = ClassCurrculWriteAdapter()
        scheduleViewModel.liveScheduleBucket.observe(viewLifecycleOwner){
            Log.d(TAG, "initAdapter: $it")
            writeAdapter.list = it
        }
        binding.fragmentCurrculWriteBucketRv.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            adapter = writeAdapter
        }

    }
    private fun initButtons(){
        writeAdapter = ClassCurrculWriteAdapter()
        binding.fragmentCurrculWriteAddBucketBtn.setOnClickListener {
            insertBucket()
            writeAdapter.notifyDataSetChanged()
        }
        binding.fragmentCurrculWriteChecked.setOnClickListener {
            if(writeAdapter.checked == true){
                binding.fragmentCurrculWriteChecked.text = "선택"

                writeAdapter.updateCheckBox(false)
                writeAdapter.notifyDataSetChanged()
            }else{
                binding.fragmentCurrculWriteChecked.text = "선택취소"
                writeAdapter.updateCheckBox(true)
                writeAdapter.notifyDataSetChanged()
            }
        }
        binding.fragmentCurrculWriteBinBtn.setOnClickListener{
            scheduleViewModel.removeScheduleBucket(writeAdapter.checkList)
            writeAdapter.notifyDataSetChanged()
            writeAdapter.checkList = mutableListOf()
        }
        binding.fragmentCurrculWriteRemoveAll.setOnClickListener {
            scheduleViewModel.removeAllScheduleBucket()
            writeAdapter.notifyDataSetChanged()
        }
        binding.fragmentCurrculWriteInsert.setOnClickListener {
            if(scheduleId>0){
                modifySchedule()
            }else{
                insertSchedules()
            }
        }
        binding.fragmentCurrculWriteAppBarPrev.setOnClickListener {
            this@CurriculumWriteFragment.findNavController().popBackStack()
        }
    }
    private fun modifySchedule(){
        if(titleType.equals("")){
            titleType = binding.fragmentCurrculWriteTitleEdit.text.toString()
        }
        var schedules:Schedule
        if(type == 0){
            schedules = Schedule(
                classRoomId = roomId,
                endDate = "${binding.fragmentCurrculWriteEndDateTv.text.toString()} ${binding.fragmentCurrculWriteEndTimeTv.text.toString().substring(3,binding.fragmentCurrculWriteEndTimeTv.length())}",
                scheduleId,
                memo = binding.fragmentCurrWriteMemoEdit.text.toString(),
                startDate = "${binding.fragmentCurrculWriteStartDateTv.text.toString()} ${binding.fragmentCurrculWriteStartTimeTv.text.toString().substring(3,binding.fragmentCurrculWriteStartTimeTv.length())}",
                title = titleType,
                ApplicationClass.sharedPreferencesUtil.getUser().id,
                week
            )
        }else{
            schedules = Schedule(
                classRoomId = roomId,
                endDate = "${binding.fragmentCurrculWriteStartDateTv.text.toString()} ${binding.fragmentCurrculWriteEndTimeTv.text.toString().substring(3,binding.fragmentCurrculWriteEndTimeTv.length())}",
                scheduleId,
                memo = binding.fragmentCurrWriteMemoEdit.text.toString(),
                startDate = "${binding.fragmentCurrculWriteStartDateTv.text.toString()} ${binding.fragmentCurrculWriteStartTimeTv.text.toString().substring(3,binding.fragmentCurrculWriteStartTimeTv.length())}",
                title = titleType,
                ApplicationClass.sharedPreferencesUtil.getUser().id,
                0
            )
        }

        runBlocking {
            val response = ScheduleService().modifySchedule(schedules)
            if(response.code() == 204){
                showCustomToast("수정되었습니다.")
                this@CurriculumWriteFragment.findNavController().navigate(R.id.scheduleFragment)
            }
        }
    }
    private fun insertSchedules(){
        var schedules = scheduleViewModel.liveScheduleBucket.value!!
        runBlocking {
            val response = ScheduleService().insertSchedule(schedules)
            if(response.code() == 204){
                this@CurriculumWriteFragment.findNavController().navigate(R.id.scheduleFragment)
            }
        }
    }
    private fun insertBucket(){
        if(titleType.equals("")){
            titleType = binding.fragmentCurrculWriteTitleEdit.text.toString()
        }
        if(type == 0){
            var schedule = Schedule(
                classRoomId = roomId,
                endDate = "${binding.fragmentCurrculWriteEndDateTv.text.toString()} ${binding.fragmentCurrculWriteEndTimeTv.text.toString().substring(3,binding.fragmentCurrculWriteEndTimeTv.length())}",
                0,
                memo = binding.fragmentCurrWriteMemoEdit.text.toString(),
                startDate = "${binding.fragmentCurrculWriteStartDateTv.text.toString()} ${binding.fragmentCurrculWriteStartTimeTv.text.toString().substring(3,binding.fragmentCurrculWriteStartTimeTv.length())}",
                title = titleType,
                ApplicationClass.sharedPreferencesUtil.getUser().id,
                week
            )
            Log.d(TAG, "insertBucket: $schedule")
            scheduleViewModel.insertScheduleBucket(schedule)
        }else{
            var schedule = Schedule(
                classRoomId = roomId,
                endDate = "${binding.fragmentCurrculWriteStartDateTv.text.toString()} ${binding.fragmentCurrculWriteEndTimeTv.text.toString().substring(3,binding.fragmentCurrculWriteEndTimeTv.length())}",
                0,
                memo = binding.fragmentCurrWriteMemoEdit.text.toString(),
                startDate = "${binding.fragmentCurrculWriteStartDateTv.text.toString()} ${binding.fragmentCurrculWriteStartTimeTv.text.toString().substring(3,binding.fragmentCurrculWriteStartTimeTv.length())}",
                title = titleType,
                ApplicationClass.sharedPreferencesUtil.getUser().id,
                0
            )
            Log.d(TAG, "insertBucket: $schedule")
            scheduleViewModel.insertScheduleBucket(schedule)
        }

    }
    private fun initTimePicker(){
        //종료시간 선택
        binding.fragmentCurrculWriteEndTimeBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            var hour = ""
            var minutes = ""
            val timeSetListener = TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                if(hourOfDay >= 10){
                    hour = hourOfDay.toString()
                }else{
                    hour = "0$hourOfDay"
                }
                if(minute >= 10){
                    minutes = minute.toString()
                }else{
                    minutes = "0$minute"
                }
                if(hour.toInt() > 12){
                    binding.fragmentCurrculWriteEndTimeTv.text = "PM ${hour}:${minutes}"
                }else{
                    binding.fragmentCurrculWriteEndTimeTv.text = "AM ${hour}:${minutes}"
                }
            }

            TimePickerDialog(requireContext(),timeSetListener,cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),true).show()
        }
        //시작시간 선택
        binding.fragmentCurrculWriteStartTimeBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            var hour = ""
            var minutes = ""
            val timeSetListener = TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                if(hourOfDay >= 10){
                    hour = hourOfDay.toString()
                }else{
                    hour = "0$hourOfDay"
                }
                if(minute >= 10){
                    minutes = minute.toString()
                }else{
                    minutes = "0$minute"
                }
                if(hour.toInt() > 12){
                    binding.fragmentCurrculWriteStartTimeTv.text = "PM ${hour}:${minutes}"
                }else{
                    binding.fragmentCurrculWriteStartTimeTv.text = "AM ${hour}:${minutes}"
                }
            }
            TimePickerDialog(requireContext(),timeSetListener,cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),true).show()
        }
    }
    private fun initDatePicker(){
        //종료날짜 선택
        binding.fragmentCurrculWriteEndDateBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            var months = ""
            var days = ""
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                if(month+1 >= 10){
                    months = (month+1).toString()
                }else{
                    months = "0${month+1}"
                }
                if(dayOfMonth >= 10){
                    days = dayOfMonth.toString()
                }else{
                    days = "0$dayOfMonth"
                }
                binding.fragmentCurrculWriteEndDateTv.text = "${year.toString()}-${months}-$days"
            }
            DatePickerDialog(requireContext(),dateSetListener,cal.get(Calendar.YEAR),cal.get(Calendar.MONDAY),cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        //시작날짜 선택
        binding.fragmentCurrculWriteStartDateBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            var months = ""
            var days = ""
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                if(month+1 >= 10){
                    months = (month+1).toString()
                }else{
                    months = "0${month+1}"
                }
                if(dayOfMonth >= 10){
                    days = dayOfMonth.toString()
                }else{
                    days = "0$dayOfMonth"
                }
                binding.fragmentCurrculWriteStartDateTv.text = "${year.toString()}-${months}-$days"
            }
            DatePickerDialog(requireContext(),dateSetListener,cal.get(Calendar.YEAR),cal.get(Calendar.MONDAY),cal.get(Calendar.DAY_OF_MONTH)).show()
        }
    }
    private fun initCheckBox(){
        //type = 0 이면 Class type = 1이면 Full
        binding.fragmentCurrculWriteTypeClass.setOnCheckedChangeListener {buttonView, isChecked ->
            if(isChecked){
                binding.fragmentCurrculWriteTypeFull.isChecked = false
                type = 0
            }else{
                binding.fragmentCurrculWriteTypeFull.isChecked = true
                type = 1
            }
            initLayout(type)
        }

        binding.fragmentCurrculWriteTypeFull.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                binding.fragmentCurrculWriteTypeClass.isChecked = false
                roomId = 0
                type = 1
            }else{
                binding.fragmentCurrculWriteTypeClass.isChecked = true
                type = 0
            }
            initLayout(type)
        }
    }
    private fun initLayout(type:Int){
        //반체크
        if(type == 0){
            roomId = userViewModel.loginUserInfo.value!!.classRoomId
            binding.constraintLayout16.visibility = View.VISIBLE
            binding.roomIdSpinnerLayout.visibility = View.GONE
            binding.fragmentCurrculWriteEndDateTv.visibility = View.VISIBLE
            binding.fragmentCurrculWriteEndDateBtn.visibility = View.VISIBLE
        }//전체체크
        else if(type == 1){
            binding.constraintLayout16.visibility = View.GONE
            binding.roomIdSpinnerLayout.visibility = View.VISIBLE
            binding.fragmentCurrculWriteEndDateTv.visibility = View.GONE
            binding.fragmentCurrculWriteEndDateBtn.visibility = View.GONE

            Log.d(TAG, "initLayout: ${userViewModel.classRommList}")
        }
    }
    private fun initSpinner(){

        binding.fragmentCurrculWriteTitleSpinner.adapter = ArrayAdapter(requireContext(),androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,title)
        binding.fragmentCurrculWriteTitleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position == 6){
                    binding.fragmentCurrculWriteTitleEdit.visibility = View.VISIBLE
                    titleType = ""
                }else{
                    binding.fragmentCurrculWriteTitleEdit.visibility = View.GONE
                    titleType = binding.fragmentCurrculWriteTitleSpinner.getItemAtPosition(position).toString()
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        var weeks = arrayListOf<String>()
        for(i in 1..30) {
            if(i >= 10){
                weeks.add("${i}주차")
            }else{
                weeks.add("0${i}주차")
            }

        }
        binding.fragmentCurrculWriteWeeksSpinner.adapter = ArrayAdapter(requireContext(),androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,weeks)
        binding.fragmentCurrculWriteWeeksSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                week = binding.fragmentCurrculWriteWeeksSpinner.getItemAtPosition(p2).toString().substring(0,2).toInt()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
        var rooms = arrayListOf<String>()
        var roomIdxs = arrayListOf<Int>()
        for(item in userViewModel.classRommList.value!!){
            if(item.classDescription.equals("전체")){
                rooms.add("${item.area}_${item.generation}기 ${item.classDescription}")
                roomIdxs.add(item.id)
            }

        }
        binding.roomListSpinner.adapter = ArrayAdapter(requireContext(), com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,rooms)
        binding.roomListSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                roomId = roomIdxs[position]

                Log.d(TAG, "onItemSelected: $roomId")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CurriculumWriteFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}