package com.ssafy.withssafy.src.dto.study

data class StudyComment(
    val content: String,
    val id: Int,
    val parent: Int,
    val studyBoard: StudyBoard,
    val user: UserX
)