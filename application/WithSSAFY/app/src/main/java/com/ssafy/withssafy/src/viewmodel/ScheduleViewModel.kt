package com.ssafy.withssafy.src.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.withssafy.src.dto.Schedule
import com.ssafy.withssafy.src.network.service.ScheduleService
import kotlinx.coroutines.launch

class ScheduleViewModel : ViewModel() {

    private val scheduleBucketList = mutableListOf<Schedule>()
    private val scheduleIndexList = arrayListOf<Int>()
    var liveScheduleBucket = MutableLiveData<MutableList<Schedule>>().apply {
        value = scheduleBucketList
    }
    var liveScheduleIndex = MutableLiveData<ArrayList<Int>>().apply {
        value = scheduleIndexList
    }
    private val _classSchedules = MutableLiveData<MutableList<Schedule>>()
    private val _schedule = MutableLiveData<Schedule>()
    private val _allClassSchedules = MutableLiveData<MutableList<Schedule>>()
    private val _allGenarateSchedules = MutableLiveData<MutableList<Schedule>>()
    private val _genarateScheduleDetails = MutableLiveData<MutableList<Schedule>>()

    val classSchedules : LiveData<MutableList<Schedule>>
        get() = _classSchedules
    val schedule : LiveData<Schedule>
        get() = _schedule
    val allClassSchedules : LiveData<MutableList<Schedule>>
        get() = _allClassSchedules
    val allGenarateSchedules : LiveData<MutableList<Schedule>>
        get() = _allGenarateSchedules
    val genarateScheduleDetails : LiveData<MutableList<Schedule>>
        get() = _genarateScheduleDetails

    fun setAllClassSchedules (list:MutableList<Schedule>) {
        _allClassSchedules.value = list
    }
    fun setClassSchedules(list : MutableList<Schedule>){
        _classSchedules.value = list
    }
    fun setSchedule(schedule: Schedule){
        _schedule.value = schedule
    }
    fun setAllGenarateSchedules(list:MutableList<Schedule>){
        _allGenarateSchedules.value = list
    }
    fun setGenarateScheduleDetails(list:MutableList<Schedule>){
        _genarateScheduleDetails.value = list
    }

    fun insertScheduleIndex(idx : Int){
        scheduleIndexList.add(idx)
        liveScheduleIndex.value = scheduleIndexList
    }
    fun insertScheduleBucket(schedule: Schedule){
        scheduleBucketList.add(schedule)
        liveScheduleBucket.value = scheduleBucketList
    }
    fun removeScheduleBucket(list:MutableList<Int>){
        for(i in 0..list.size-1){
            scheduleBucketList.removeAt(i)
        }
        liveScheduleBucket.value = scheduleBucketList
    }
    fun removeAllScheduleBucket(){
        scheduleBucketList.clear()
        liveScheduleBucket.value = scheduleBucketList
    }
    suspend fun getClassSchedule(id:Int){
        val response = ScheduleService().getSchedulesByMyClass(id)
        viewModelScope.launch {
            val res = response.body()
            if(response.code()== 200){
                if(res!=null){
                    setClassSchedules(res)
                }
            }
        }
    }
    suspend fun getScheduleById(id:Int){
        val response = ScheduleService().getScheduleById(id)
        viewModelScope.launch {
            val res = response.body()
            if(response.code() == 200){
                if(res!=null){
                    setSchedule(res)
                }
            }
        }
    }

    suspend fun getAllClassSchedules(id:Int){
        val response = ScheduleService().getAllMyClassSchedule(id)
        viewModelScope.launch {
            val res = response.body()
            if(response.code() == 200){
                if(res!=null){
                    setAllClassSchedules(res)
                }
            }
        }
    }

    suspend fun getAllGenarateSchedules(id:Int){
        val response = ScheduleService().getAllMyGenarateSchedule(id)
        viewModelScope.launch {
            val res = response.body()
            if(response.isSuccessful){
                if(response.code() == 200){
                    if(res!=null){
                        setAllGenarateSchedules(res)
                    }
                }
            }
        }
    }

    suspend fun getGenarateScheduleDetails(day:String, id:Int){
        val response = ScheduleService().getGenarateScheduleDetail(id,day)
        viewModelScope.launch {
            val res = response.body()
            if(response.isSuccessful){
                if(response.code() == 200){
                    if(res!=null){
                        setGenarateScheduleDetails(res)
                    }
                }
            }
        }
    }
}