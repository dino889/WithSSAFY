package com.ssafy.withssafy.src.dto.report

data class ReportRequest(
    val id: Int,
    val board: Int?,
    val comment: Int?,
    val content: String,
    val user: Int
)