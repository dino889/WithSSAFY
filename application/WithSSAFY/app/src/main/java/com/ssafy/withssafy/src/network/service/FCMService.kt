package com.ssafy.withssafy.src.network.service

import com.ssafy.withssafy.src.dto.FcmRequest
import com.ssafy.withssafy.util.RetrofitUtil

/**
 * @since 05/03/22
 * @author Jiwoo Choi
 */
class FcmService {

    suspend fun sendToMsg(fcmRequest: FcmRequest, token: String) = RetrofitUtil.fcmService.sendMessageTo(fcmRequest, token)

    suspend fun broadCastMsg(fcmRequest: FcmRequest) = RetrofitUtil.fcmService.broadCast(fcmRequest)
}