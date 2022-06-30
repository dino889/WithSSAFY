package com.ssafy.withssafy.src.network.api

import com.ssafy.withssafy.src.dto.report.Report
import com.ssafy.withssafy.src.dto.report.ReportRequest
import retrofit2.Response
import retrofit2.http.*

interface ReportApi {

    @GET("report")
    suspend fun selectReportList() : Response<List<Report>>

    @POST("report")
    suspend fun insertReport(@Body reportDto: ReportRequest) : Response<List<Report>>

    @DELETE("report")
    suspend fun deleteReport(@Query("id") id: Int) : Response<Report>

    /**
     * 해당 게시글을 신고한 유저 목록을 불러온다
     */
    @GET("report/board")
    suspend fun selectUserListByBoardId(@Query("boardId") boardId: Int) : Response<List<Int>>

    /**
     * 해당 댓글을 신고한 유저 목록을 불러온다
     */
    @GET("report/comment")
    suspend fun selectUserListByCommentId(@Query("commentId") commentId: Int) : Response<List<Int>>
}