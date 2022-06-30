package com.ssafy.withssafy.src.dto.report

data class Report(
    val id: Int,
    val board: Board?,
    val comment: Comment?,
    val user: User,
    val content: String,
    val write_dt: String
)