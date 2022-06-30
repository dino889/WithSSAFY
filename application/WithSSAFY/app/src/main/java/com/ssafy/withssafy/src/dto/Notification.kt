package com.ssafy.withssafy.src.dto

data class Notification(
    val content: String,
    val dateTime: String,
    val id: Int,
    val title: String,
    val type: Int,
    val user: User
)