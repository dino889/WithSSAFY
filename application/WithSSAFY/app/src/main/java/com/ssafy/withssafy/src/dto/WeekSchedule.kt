package com.ssafy.withssafy.src.dto

data class WeekSchedule(
    val weeks:Int,
    val schedules:ArrayList<com.github.tlaabs.timetableview.Schedule>,
    val scheduleDtos : MutableList<Schedule>
)