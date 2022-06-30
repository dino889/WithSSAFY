package com.ssafy.withssafy.src.dto

data class FcmRequest(
    val body: String,
    val img: String?,
    val title: String,
    val type: Int
) {
    constructor(type: Int, title: String, body: String) : this(type = type, title = title, body = body, img = null)
}