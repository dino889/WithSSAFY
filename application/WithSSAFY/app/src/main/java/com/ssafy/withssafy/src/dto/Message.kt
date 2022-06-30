package com.ssafy.withssafy.src.dto

data class Message(
    val content: String,
    val id: Int,
    val send_dt: String,
    val u_fromId: Int,
    val u_toId: Int
){
    constructor(content:String,id:Int,u_fromId:Int,u_toId: Int):this(content, id, "", u_fromId, u_toId)
}