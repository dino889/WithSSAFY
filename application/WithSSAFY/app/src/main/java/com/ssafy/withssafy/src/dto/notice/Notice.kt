package com.ssafy.withssafy.src.dto.notice

data class Notice(
    val id : Int,
    val classRoomId: Int,
    val content: String,
    val filePath: String,
    val photoPath: String,
    val title: String,
    val typeId: Int,
    val userId: Int,
    val writeDt : String
) {
    constructor(classRoomId: Int, content: String, title: String, typeId: Int, userId: Int) : this(id = 0, classRoomId = classRoomId, content = content, filePath = "", photoPath = "",
    title = title, typeId = typeId, userId = userId, writeDt = "")
    constructor(classRoomId: Int, content: String, photoPath: String, title: String, typeId: Int, userId: Int) : this(id = 0, classRoomId = classRoomId, content = content, filePath = "", photoPath = photoPath,
        title = title, typeId = typeId, userId = userId, writeDt = "")
}