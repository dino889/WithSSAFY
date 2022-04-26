package com.ssafy.withssafy.src.dto

data class Study(
    val area: String,
    val category: String,
    val content: String,
    val id: Int,
    val isOuting: Int,
    val photoFile: PhotoFile,
    val sbLimit: Int,
    val title: String,
    val user: UserX,
    val writeDateTime: String
)