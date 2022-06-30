package com.ssafy.withssafy.src.dto.report

data class User(
    val classRoomId: Int,
    val deviceToken: String,
    val id: Int,
    val name: String,
    val password: String,
    val state: Int,
    val studentId: String,
    val userId: String
)