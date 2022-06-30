package com.ssafy.withssafy.src.dto.report

data class Board(
    val boardType: BoardType,
    val commentCount: Int,
    val comments: List<Any>,
    val content: String,
    val id: Int,
    val likeCount: Int,
    val photoPath: String,
    val title: String,
    val user: UserX,
    val writeDateTime: String
)