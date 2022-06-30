package com.ssafy.withssafy.src.dto.notice

data class NoticeRequest(
    val content: String,
    val filePath: String,
    val photoPath: String,
    val title: String
) {
    constructor(content: String, title: String) : this(content = content, filePath = "", photoPath = "", title = title)
    constructor(content: String, photoPath: String, title: String) : this(content = content, filePath = "", photoPath = photoPath, title = title)
}