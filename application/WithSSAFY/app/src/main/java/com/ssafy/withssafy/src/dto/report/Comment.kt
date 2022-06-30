package com.ssafy.withssafy.src.dto.report

data class Comment(
    val boardId: Int,
    val content: String,
    val id: Int,
    val parentId: Int,
    val userId: Int,
    val write_dt: String
)