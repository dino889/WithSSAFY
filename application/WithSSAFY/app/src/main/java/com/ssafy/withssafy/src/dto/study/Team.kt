package com.ssafy.withssafy.src.dto.study

data class Team(
    val classification: Int,
    val id: Int,
    val maxLimit: Int,
    val minLimit: Int,
    val options: String
)