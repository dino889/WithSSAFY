package com.ssafy.withssafy.src.network.service

import android.util.JsonToken
import android.util.Log
import com.ssafy.withssafy.src.dto.ClassRoom
import com.ssafy.withssafy.src.dto.User
import com.ssafy.withssafy.src.network.response.UserInfoResponse
import com.ssafy.withssafy.util.RetrofitCallback
import com.ssafy.withssafy.util.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserService {
    suspend fun getUserList() = RetrofitUtil.userService.selectUserList()

    suspend fun getClassRoomList() = RetrofitUtil.userService.selectClassRoomList()

    // 회원가입
    fun signUp(user: User, callback: RetrofitCallback<User>) {
        RetrofitUtil.userService.signUp(user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val res = response.body()
                if(response.code() == 200) {
                    if (res != null) {
                        callback.onSuccess(response.code(), res)
                    }
                } else {
                    callback.onFailure(response.code())
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                callback.onError(t)
            }
        })
    }

    // 관리자 회원가입
    fun signUpManager(status: Int, user: User, callback: RetrofitCallback<Any>) {
        RetrofitUtil.userService.signUpManager(status, user).enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                val res = response.body()
                if(response.code() == 200) {
                    if (res != null) {
                        callback.onSuccess(response.code(), res)
                    }
                } else {
                    callback.onFailure(response.code())
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                callback.onError(t)
            }
        })
    }

    // 로그인
    fun login(user: User, callback: RetrofitCallback<UserInfoResponse>) {
        RetrofitUtil.userService.login(user.password, user.userId).enqueue(object : Callback<UserInfoResponse> {
            override fun onResponse(call: Call<UserInfoResponse>, response: Response<UserInfoResponse>) {
                Log.d("UserService", "onResponse: ${user.password} ${user.userId}")
                val res = response.body()
                if(response.code() == 200){
                    if (res != null) {
                        callback.onSuccess(response.code(), res)
                        Log.d("UserService", "onResponse: $res")
                    }
                } else {
                    callback.onFailure(response.code())
                }
            }
            override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                callback.onError(t)
            }
        })
    }

    // 특정 사용자 조회
    suspend fun getUser(userId: Int) : Response<User> {
        return RetrofitUtil.userService.getUser(userId)
    }

    // 사용자 계정 삭제
    fun deleteUser(id: Int, callback:RetrofitCallback<Boolean>){
        RetrofitUtil.userService.deleteUser(id).enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                val res = response.body()
                if(response.code() == 200) {
                    if (res != null) {
                        callback.onSuccess(response.code(), res)
                    }
                } else {
                    callback.onFailure(response.code())
                }
            }
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                callback.onError(t)
            }

        })
    }

    fun updatePw(pw: String, id: Int, callback:RetrofitCallback<User>){
        RetrofitUtil.userService.updatePw(pw, id).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val res = response.body()
                if(response.code() == 200) {
                    if (res != null) {
                        callback.onSuccess(response.code(), res)
                    }
                } else {
                    callback.onFailure(response.code())
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                callback.onError(t)
            }

        })
    }

    fun updateClass(classId: Int, id: Int, callback:RetrofitCallback<User>){
        RetrofitUtil.userService.updateClass(classId, id).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val res = response.body()
                if(response.code() == 200) {
                    if (res != null) {
                        callback.onSuccess(response.code(), res)
                    }
                } else {
                    callback.onFailure(response.code())
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                callback.onError(t)
            }

        })
    }

    // ClassRoom 조회
    suspend fun getClassRoom(id: Int) : Response<ClassRoom> {
        return RetrofitUtil.userService.getClassRoom(id)
    }

    suspend fun updateState(id: Int, state :Int) = RetrofitUtil.userService.updateState(id, state)


    suspend fun selectStateZeroUser() = RetrofitUtil.userService.selectStateZeroUser()

    suspend fun updateUserToken(userId: Int, token: String) = RetrofitUtil.userService.updateUserDeviceToken(userId, token)

    suspend fun getUserStatus(userId: Int) = RetrofitUtil.userService.getUserStatus(userId)
}