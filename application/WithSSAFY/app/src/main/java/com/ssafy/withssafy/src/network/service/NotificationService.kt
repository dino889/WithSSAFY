package com.ssafy.withssafy.src.network.service

import com.ssafy.withssafy.util.RetrofitUtil

class NotificationService {
    suspend fun selectNotificationByUser(userId : Int) = RetrofitUtil.notificationApi.selectNotificationByUser(userId)

    suspend fun selectNotificationByType(type : Int, userId : Int) = RetrofitUtil.notificationApi.selectNotificationByType(type, userId)

    suspend fun deleteNotificationById(id: Int) = RetrofitUtil.notificationApi.deleteNotificationById(id)
}