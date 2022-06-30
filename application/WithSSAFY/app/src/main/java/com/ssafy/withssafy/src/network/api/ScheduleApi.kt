package com.ssafy.withssafy.src.network.api

import com.ssafy.withssafy.src.dto.Schedule
import retrofit2.Response
import retrofit2.http.*

interface ScheduleApi {
    /**
     * 스케줄 추가하기 [어드민기능 ]
     * Insert Schedule to Admin
     * @author : LeeBoYeon
     * */
    @POST("/schedule")
    suspend fun insertSchedule(@Body schedules:List<Schedule>) : Response<Any?>
    /**
     * 스케줄 수정하기 [어드민기능 ]
     * Modify Schedule to Admin
     * @author : LeeBoYeon
     * */
    @PUT("/schedule")
    suspend fun modifySchedule(@Body schedule: Schedule) : Response<Any?>
    /**
     * 스케줄 아이디로 하나 조회하기
     * Get Schedule By Id
     * @author : LeeBoYeon
     * */
    @GET("/schedule/{id}")
    suspend fun getSchedulesById(@Path("id")id:Int) : Response<Schedule>
    /**
     * 우리반 스케줄 일주일치 조회하기
     * Get MyClass Schedule To Week
     * @author : LeeBoYeon
     * */
    @GET("/schedule/myclass/{id}")
    suspend fun getSchedulesByMyClass(@Path("id")id:Int) : Response<MutableList<Schedule>>
    /**
     * 스케줄 삭제하기
     * Delete Schedule By Id
     * @author : LeeBoYeon
     * */
    @DELETE("/schedule/{id}")
    suspend fun deleteSchdule(@Path("id")id:Int):Response<Any?>
    /**
     * 모든 스케줄 가져오기
     * Get All MyClass Schedule
     * @author : LeeBoYeon
     * */
    @GET("/schedule/myclass/all/{id}")
    suspend fun getAllMyClassSchedule(@Path("id")id:Int) : Response<MutableList<Schedule>>
    /**
     * 내 기수 전체 일정 조회
     * Get All Generation Schedule
     * @author : LeeBoYeon
     * */
    @GET("/schedule/all/{id}")
    suspend fun getAllMyGenarateSchedule(@Path("id")id:Int) : Response<MutableList<Schedule>>
    /**
     * 내 기수 특정 날짜 전체 일정 조회
     * Get All Generation Schedule by Date
     * @author : LeeBoYeon
     * */
    @GET("/schedule/all/day/{id}")
    suspend fun getGenarateScheduleDetail(@Path("id")id:Int,@Query("day")day:String) : Response<MutableList<Schedule>>

}