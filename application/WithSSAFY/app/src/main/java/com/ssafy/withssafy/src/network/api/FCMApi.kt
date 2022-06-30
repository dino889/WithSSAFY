package com.ssafy.withssafy.src.network.api

import com.ssafy.withssafy.src.dto.FcmRequest
import retrofit2.Response
import retrofit2.http.*

interface FCMApi {

    /**
     * 사용자의 token으로 fcm 전송
     */
    @POST("fcm/sendMessageTo")
    suspend fun sendMessageTo(@Body fcmRequest: FcmRequest, @Query("token") token: String): Response<Any?>

    /**
     * 전체 사용자에게 fcm 전송
     */
    @POST("fcm/broadcast")
    suspend fun broadCast(@Body fcmRequest: FcmRequest) : Response<Any?>
}