package com.ssafy.withssafy.src.dto.study

data class StudyMemberRequest (
    val studyBoardId: Int,
    val userId: Int
        ){
    constructor(userId:Int):this(0,userId)
}