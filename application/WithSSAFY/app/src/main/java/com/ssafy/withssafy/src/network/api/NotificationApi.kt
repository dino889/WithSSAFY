package com.ssafy.withssafy.src.network.api

import com.ssafy.withssafy.src.dto.Notification
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NotificationApi {

    /**
     * 회원 계정 아이디에 일치하는 모든 알림을 조회
     * @param userId
     */
    @GET("/notification/user")
    suspend fun selectNotificationByUser(@Query("userId") userId : Int) : Response<MutableList<Notification>>

    /**
     * user와 type에 일치하는 모든 알림을 조회
     * @param type
     * @param userId
     */
    @GET("/notification/user/type")
    suspend fun selectNotificationByType(@Query("type") type : Int, @Query("userId") userId : Int) : Response<MutableList<Notification>>

    /**
     * id에 해당하는 알림 삭제
     * @param id
     */
    @DELETE("/notification")
    suspend fun deleteNotificationById(@Query("id") id: Int) : Response<Any?>

}