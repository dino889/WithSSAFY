package com.ssafy.withssafy.src.dto.study

import com.ssafy.withssafy.src.dto.UserX

data class Study(
    val area: String,
    val category: String,
    val content: String,
    val id: Int?,
    val isOuting: Int,
    val sbLimit: Int,
    val studyMembers: List<StudyMember>?,
    val title: String,
    val user: UserX?,
    val writeDateTime: String,
    val photoPath: String?,
    val userId:Int,
    val type:Int
){
    constructor(area: String,category: String, content:String, id: Int,isOuting: Int,sbLimit: Int,title: String,writeDateTime: String,userId: Int,type:Int): this(area, category, content, id, isOuting, sbLimit, null, title, null, writeDateTime, "", userId,type)
    constructor(area: String,category: String, content:String, id: Int,isOuting: Int,sbLimit: Int,title: String,writeDateTime: String, photoPath: String,userId: Int,type:Int): this(area, category, content, id, isOuting, sbLimit, null, title, null, writeDateTime, photoPath, userId,type)
    constructor(area: String,category: String,content: String,isOuting: Int,sbLimit: Int,title: String,writeDateTime: String,userId: Int,type:Int) : this(area, category, content, null, isOuting, sbLimit, null, title, null, writeDateTime, "", userId,type)
}
