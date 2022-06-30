package com.ssafy.withssafy.src.dto.board

import com.ssafy.withssafy.src.dto.study.StudyBoard
import com.ssafy.withssafy.src.dto.study.UserX

data class Comment(
    val id: Int,
    val parentId: Int?,
    val boardId: Int,
    val userId: Int,
    val content: String,
    val write_dt: String?,
    val studyBoard: StudyBoard?,
    val user: UserX?
) {
    constructor(boardId: Int, userId: Int, content: String) : this(0, null, boardId, userId, content, null,null,null)  // 댓글 입력
    constructor(parent: Int, boardId: Int, userId: Int, content: String) : this(0, parent, boardId, userId, content, null,null,null)  // 대댓글 입력
    constructor(id: Int, content: String) : this(id, 0, 0, 0, content, null,null,null)
}