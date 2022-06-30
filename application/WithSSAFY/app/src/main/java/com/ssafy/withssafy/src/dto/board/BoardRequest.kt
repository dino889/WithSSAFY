package com.ssafy.withssafy.src.dto.board

data class BoardRequest(
    val id: Int,
    val typeId: Int,
    val userId: Int,
    val title: String,
    val content: String,
    val photoPath: String,
    val writeDateTime: String?
) {
    // insert
    constructor(typeId: Int, userId: Int, title: String, content: String, photoPath: String) : this(id = 0, typeId = typeId, userId = userId, title = title, content = content, photoPath = photoPath, null)

    // update
    constructor(id: Int, title: String, content: String, photoPath: String) : this(id = id, typeId = 0, userId = 0, title = title, content = content, photoPath = photoPath, null)
}