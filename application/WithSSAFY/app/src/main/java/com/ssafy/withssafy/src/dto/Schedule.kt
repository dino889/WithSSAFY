package com.ssafy.withssafy.src.dto

data class Schedule(
    val classRoomId: Int,
    val endDate: String,
    val id: Int,
    val memo: String?,
    val startDate: String,
    val title: String,
    val userId: Int,
    val weeks:Int
)