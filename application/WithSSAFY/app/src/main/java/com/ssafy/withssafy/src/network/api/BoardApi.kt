package com.ssafy.withssafy.src.network.api

import com.ssafy.withssafy.src.dto.board.Board
import com.ssafy.withssafy.src.dto.board.BoardRequest
import com.ssafy.withssafy.src.dto.board.BoardType
import com.ssafy.withssafy.src.dto.board.LikeDto
import retrofit2.Response
import retrofit2.http.*

/**
 * @since 04/26/22
 * @author Jiwoo Choi
 */
interface BoardApi {

    /**
     * 모든 게시판 조회
     */
    @GET("board-types")
    suspend fun selectAllBoardType() : Response<List<BoardType>>

    /**
     * 모든 게시글 조회
     * boardType dto + 게시글 내용 + user Dto
     */
    @GET("boards")
    suspend fun selectAllList() : Response<List<Board>>

    /**
     * 게시글 등록
     */
    @POST("boards")
    suspend fun insertPost(@Body boardRequest : BoardRequest) : Response<Any?>

    /**
     * 게시글 상세 조회
     * boardType dto + 게시글 내용 + user Dto
     */
    @GET("boards/{id}")
    suspend fun selectPostById(@Path("id") id: Int) : Response<Board>

    /**
     * 게시글 수정
     */
    @PUT("boards/{id}")
    suspend fun updatePostById(@Path("id") id: Int, @Body boardRequest: BoardRequest) : Response<Any?>

    /**
     * 게시글 삭제
     */
    @DELETE("boards/{id}")
    suspend fun deletePostById(@Path("id") id: Int) : Response<Any?>

    /**
     * 사용자가 댓글 단 게시물 조회
     */
    @GET("boards/comment/{id}")
    suspend fun selectPostInComment(@Path("id") userId: Int) : Response<List<Board>>

    /**
     * 좋아요 10개 이상 Hot 게시물 조회
     */
    @GET("boards/hot-board")
    suspend fun getHotPostList() : Response<List<Board>>

    /**
     * 게시물 좋아요 확인
     */
    @GET("boards/like")
    suspend fun postLikeOrNot(@Query("boardId") boardId: Int, @Query("userId") userId: Int) : Response<Boolean>

    /**
     * 게시물 좋아요 or 취소
     */
    @POST("boards/like")
    suspend fun postLike(@Body likeDto: LikeDto) : Response<Any?>

    /**
     * 사용자가 좋아요 누른 게시물 조회
     */
    @GET("boards/liked-board")
    suspend fun likePostByUser(@Query("uid") userId: Int) : Response<List<Board>>

    /**
     * 게시판 타입별 게시글 조회
     */
    @GET("boards/q")
    suspend fun selectBoardListByTypeId(@Query("type") type: Int) : Response<List<Board>>

    /**
     * user 작성한 게시글 조회
     */
    @GET("boards/q")
    suspend fun selectBoardListByUserId(@Query("UID") userId: Int) : Response<List<Board>>

}