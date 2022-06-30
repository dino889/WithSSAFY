package com.ssafy.withssafy.src.network.service

import com.ssafy.withssafy.src.dto.Schedule
import com.ssafy.withssafy.util.RetrofitUtil
import retrofit2.Response

class ScheduleService {

    suspend fun insertSchedule(schedules:List<Schedule>) : Response<Any?> = RetrofitUtil.schduleApi.insertSchedule(schedules)

    suspend fun modifySchedule(schedule:Schedule) : Response<Any?> = RetrofitUtil.schduleApi.modifySchedule(schedule)

    suspend fun deleteSchedule(id:Int) : Response<Any?> = RetrofitUtil.schduleApi.deleteSchdule(id)

    suspend fun getScheduleById(id:Int) : Response<Schedule> = RetrofitUtil.schduleApi.getSchedulesById(id)

    suspend fun getSchedulesByMyClass(id:Int) : Response<MutableList<Schedule>> = RetrofitUtil.schduleApi.getSchedulesByMyClass(id)

    suspend fun getAllMyClassSchedule(id:Int) : Response<MutableList<Schedule>> = RetrofitUtil.schduleApi.getAllMyClassSchedule(id)

    suspend fun getAllMyGenarateSchedule(id:Int) : Response<MutableList<Schedule>> = RetrofitUtil.schduleApi.getAllMyGenarateSchedule(id)

    suspend fun getGenarateScheduleDetail(id:Int, day:String) : Response<MutableList<Schedule>> = RetrofitUtil.schduleApi.getGenarateScheduleDetail(id,day)
}