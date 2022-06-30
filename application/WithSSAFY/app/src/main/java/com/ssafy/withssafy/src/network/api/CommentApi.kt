package com.ssafy.withssafy.src.network.api

import com.ssafy.withssafy.src.dto.board.Comment
import retrofit2.Response
import retrofit2.http.*

/**
 * @since 04/27/22
 * @author Jiwoo Choi
 */
interface CommentApi {

    /**
     * comment 테이블 전체 조회
     *
     */
    @GET("comment")
    suspend fun selectCommentAll() : Response<List<Comment>>

    /**
     * 댓글 등록
     * parent == 0 : 댓글
     * parent == commentId : 대댓글
     * @param commentDto
     */
    @POST("comment")
    suspend fun insertComment(@Body commentDto: Comment) : Response<Any?>

    /**
     * 댓글 수정
     * id, content 필수
     * @param commentDto
     */
    @PUT("comment")
    suspend fun updateComment(@Body commentDto: Comment) : Response<Any?>

    /**
     * 댓글 삭제
     * @param commentId
     */
    @DELETE("comment")
    suspend fun deleteComment(@Query("id") commentId: Int) : Response<Any?>

    /**
     * 게시글에 해당하는 댓글 조회
     * @param boardId
     */
    @GET("comment/{boardId}")
    suspend fun selectCommentByBoardId(@Path("boardId") boardId: Int) : Response<List<Comment>>

    /**
     * 사용자가 작성한 댓글 조회
     * @param userId
     */
    @GET("comment/user/{userId}")
    suspend fun selectCommentByUserId(@Path("userId") userId: Int) : Response<List<Comment>>

}