package com.ssafy.withssafy.src.network.response

import com.google.gson.annotations.SerializedName
import com.ssafy.withssafy.src.dto.ClassRoom
import java.io.Serializable

data class UserInfoResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("classRoomDto") var classRoomDto: ClassRoom,
    @SerializedName("userId") var userId: String,
    @SerializedName("password") var password: String,
    @SerializedName("state") var state: Int,
    @SerializedName("deviceToken") var deviceToken: String,
    @SerializedName("studentId") var studentId: String,
    @SerializedName("name") var name: String,
) : Serializable
