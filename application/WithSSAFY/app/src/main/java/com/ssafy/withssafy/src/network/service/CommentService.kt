package com.ssafy.withssafy.src.network.service

import com.ssafy.withssafy.src.dto.board.Comment
import com.ssafy.withssafy.util.RetrofitUtil

/**
 * @since 04/27/22
 * @author Jiwoo Choi
 */
class CommentService {

    suspend fun getAllComment() = RetrofitUtil.commentApi.selectCommentAll()

    suspend fun addComment(commentDto: Comment) = RetrofitUtil.commentApi.insertComment(commentDto)

    suspend fun modifyComment(commentDto: Comment) = RetrofitUtil.commentApi.updateComment(commentDto)

    suspend fun deleteComment(commentId: Int) = RetrofitUtil.commentApi.deleteComment(commentId)

    suspend fun getCommentListByBoardId(boardId: Int) = RetrofitUtil.commentApi.selectCommentByBoardId(boardId)

    suspend fun getCommentListByUserId(userId: Int) = RetrofitUtil.commentApi.selectCommentByUserId(userId)
}