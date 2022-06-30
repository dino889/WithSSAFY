package com.ssafy.withssafy.src.network.service

import com.ssafy.withssafy.src.dto.board.Board
import com.ssafy.withssafy.src.dto.board.BoardRequest
import com.ssafy.withssafy.src.dto.board.LikeDto
import com.ssafy.withssafy.util.RetrofitUtil

/**
 * @since 04/26/22
 * @author Jiwoo Choi
 */
class BoardService {

    suspend fun getAllBoardTType() = RetrofitUtil.boardApi.selectAllBoardType()

    suspend fun getAllList() = RetrofitUtil.boardApi.selectAllList()

    suspend fun addPost(boardRequest: BoardRequest) = RetrofitUtil.boardApi.insertPost(boardRequest)

    suspend fun getPostDetail(postId: Int) = RetrofitUtil.boardApi.selectPostById(postId)

    suspend fun modifyPost(postId: Int, boardRequest: BoardRequest) = RetrofitUtil.boardApi.updatePostById(postId, boardRequest)

    suspend fun deletePost(postId: Int) = RetrofitUtil.boardApi.deletePostById(postId)

    suspend fun getPostInAComment(userId: Int) = RetrofitUtil.boardApi.selectPostInComment(userId)

    suspend fun getHotPostList() = RetrofitUtil.boardApi.getHotPostList()

    suspend fun postLikeOrNot(boardId: Int, userId: Int) = RetrofitUtil.boardApi.postLikeOrNot(boardId, userId)

    suspend fun likePost(likeDto: LikeDto) = RetrofitUtil.boardApi.postLike(likeDto)

    suspend fun likePostByUser(userId: Int) = RetrofitUtil.boardApi.likePostByUser(userId)

    suspend fun getBoardListByTypeId(type: Int) = RetrofitUtil.boardApi.selectBoardListByTypeId(type)

    suspend fun getBoardListByUserId(userId: Int) = RetrofitUtil.boardApi.selectBoardListByUserId(userId)

}