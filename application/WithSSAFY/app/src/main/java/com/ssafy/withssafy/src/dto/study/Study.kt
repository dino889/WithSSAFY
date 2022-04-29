package com.ssafy.withssafy.src.dto.study

import com.ssafy.withssafy.src.dto.UserX

data class Study(
    val area: String,
    val category: String,
    val content: String,
    val id: Int,
    val isOuting: Int,
    val sbLimit: Int,
    val studyMember: List<StudyMember>?,
    val title: String,
    val user: UserX?,
    val writeDateTime: String,
    val photoFile: String?,
    val userId:Int
){
    constructor(area: String,category: String, content:String, id: Int,isOuting: Int,sbLimit: Int,title: String,writeDateTime: String,userId: Int): this(area, category, content, id, isOuting, sbLimit, null, title, null, writeDateTime, "", userId)
    constructor(area: String,category: String, content:String, id: Int,isOuting: Int,sbLimit: Int,title: String,writeDateTime: String, photoFile: String,userId: Int): this(area, category, content, id, isOuting, sbLimit, null, title, null, writeDateTime, photoFile, userId)
}