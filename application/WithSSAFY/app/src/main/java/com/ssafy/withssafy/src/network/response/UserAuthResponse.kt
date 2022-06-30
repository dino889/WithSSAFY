package com.ssafy.withssafy.src.network.response

import com.google.gson.annotations.SerializedName
import com.ssafy.withssafy.src.dto.ClassRoom
import com.ssafy.withssafy.src.dto.User
import java.io.Serializable

data class UserAuthResponse(
    @SerializedName("auth") var auth: Int,
    @SerializedName("user") var user: User,
) : Serializable
