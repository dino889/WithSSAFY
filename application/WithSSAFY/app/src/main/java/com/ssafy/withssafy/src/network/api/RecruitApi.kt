package com.ssafy.withssafy.src.network.api

import com.ssafy.withssafy.src.dto.Recruit
import com.ssafy.withssafy.src.dto.RecruitLike
import retrofit2.Response
import retrofit2.http.*

/**
 * @since 04/28/22
 * @author KyungHee Song
 */
interface RecruitApi {

    /**
     * 채용 공고 전체 조회
     *
     */
    @GET("/recruit")
    suspend fun selectRecruitAll() : Response<MutableList<Recruit>>

    /**
     * 채용 공고 등록
     * @param recruitDto
     */
    @POST("/recruit")
    suspend fun insertRecruit(@Body recruitDto : Recruit) : Response<Any?>

    /**
     * 채용 공고 수정
     * @param recruitDto
     */
    @PUT("/recruit")
    suspend fun updateRecruit(@Body recruitDto : Recruit) : Response<Any?>

    /**
     * id에 해당하는 채용 공고 조회
     * @param id
     */
    @GET("/recruit/{id}")
    suspend fun selectRecruitById(@Path("id") id: Int) : Response<Recruit>

    /**
     * id에 해당하는 채용 공고 삭제
     * @param id
     */
    @DELETE("/recruit/{id}")
    suspend fun deleteRecruitById(@Path("id") id: Int) : Response<Any?>

    /**
     * 채용 공고 찜하기 & 취소
     * @param recruitLike
     */
    @POST("/recruit/like")
    suspend fun likeAndCancelRecruit(@Body recruitLikeDto : RecruitLike) : Response<Any?>

    /**
     * 채용 공고 찜하기 여부
     * @param recruitId
     * @param userId
     */
    @GET("/recruit/like")
    suspend fun isLikeRecruit(@Query("recruitId") recruitId: Int, @Query("userId") userId: Int) : Response<Boolean>

    /**
     * 찜한 채용공고 목록
     * @param id
     */
    @GET("/recruit/like/{id}")
    suspend fun likeRecruitList(@Path("id") id: Int) : Response<MutableList<Recruit>>
}