package com.ssafy.withssafy.src.network.api

import com.ssafy.withssafy.src.dto.notice.Notice
import com.ssafy.withssafy.src.dto.Recruit
import com.ssafy.withssafy.src.dto.notice.NoticeRequest
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

/**
 * @since 05/03/22
 * @author KyungHee Song
 */
interface NoticeApi {
    /**
     * 공지사항 목록 조회
     * @param classRoomId
     */
    @GET("/notice")
    suspend fun selectNoticeList(@Query("classRoomId") classRoomId : Int) : Response<MutableList<Notice>>

    /**
     * 공지사항 추가
     * @param noticeReqDto
     */
    @POST("/notice")
    suspend fun insertNotice(@Body noticeReqDto : Notice) : Response<Any?>

    /**
     * id에 해당하는 공지사항 조회
     * @param id
     */
    @GET("/notice/{id}")
    suspend fun selectNoticeById(@Path("id") id: Int) : Response<Notice>

    /**
     * 공지사항 수정
     * @param id
     * @param noticeReqDto
     */
    @PUT("/notice/{id}")
    suspend fun updateNotice(@Path("id") id : Int, @Body noticeReqDto : NoticeRequest) : Response<Any?>

    /**
     * id에 해당하는 공지사항 삭제
     * @param id
     */
    @DELETE("/notice/{id}")
    suspend fun deleteNoticeById(@Path("id") id: Int) : Response<Any?>

    /**
     * 공지사항 사진 추가
     * @param id
     * */
    @Multipart
    @POST("/upload")
    suspend fun insertNoticePhoto(@Part file: MultipartBody.Part) : Response<String>
}