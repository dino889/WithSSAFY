package com.ssafy.withssafy.src.dto.study

data class StudyBoard(
    val area: String,
    val category: String,
    val content: String,
    val id: Int,
    val isOuting: Int,
    val photoFile: String,
    val sbLimit: Int,
    val studyMembers: List<StudyMember>,
    val title: String,
    val user: User,
    val writeDateTime: String
)