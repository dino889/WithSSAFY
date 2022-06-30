package com.ssafy.withssafy.src.dto

data class RecruitLike(
    val id: Int,
    val recruitId: Int,
    val userId: Int
) {
    constructor(recruitId: Int, userId: Int) : this(id = 0, recruitId = recruitId, userId = userId)
}