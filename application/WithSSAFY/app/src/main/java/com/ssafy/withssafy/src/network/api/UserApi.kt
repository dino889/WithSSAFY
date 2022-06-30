package com.ssafy.withssafy.src.network.api

import com.ssafy.withssafy.src.dto.ClassRoom
import com.ssafy.withssafy.src.dto.Recruit
import com.ssafy.withssafy.src.dto.User
import com.ssafy.withssafy.src.network.response.UserAuthResponse
import com.ssafy.withssafy.src.network.response.UserInfoResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @GET("user")
    suspend fun selectUserList(): Response<List<User>>

    // 회원가입
    @POST("/user")
    fun signUp(@Body user:User) : Call<User>

    // 관리자 회원가입
    @POST("/user/manager")
    fun signUpManager(@Query("status") status: Int, @Body user:User) : Call<Any>

    // 로그인
    @GET("/user/login")
    fun login(@Query("비밀번호") password: String, @Query("아이디") userId: String): Call<UserInfoResponse>

    // 특정 사용자 조회
    @GET("/user/{id}")
    suspend fun getUser(@Path("id") id: Int): Response<User>

    // 사용자 계정 삭제
    @DELETE("/user")
    fun deleteUser(@Query("아이디") id: Int) : Call<Boolean>

    // 비밀번호 변경
    @PATCH("/user")
    fun updatePw(@Query("비밀번호") pw: String, @Query("아이디") id: Int) : Call<User>

    // 반정보 변경
    @PATCH("/user/class")
    fun updateClass(@Query("classId") classId: Int, @Query("id") id: Int) : Call<User>

    // ClassRoom
    @GET("/classroom/all")
    suspend fun selectClassRoomList(): Response<MutableList<ClassRoom>>

    // ClassRoom 조회
    @GET("/classroom")
    suspend fun getClassRoom(@Query("id") id: Int): Response<ClassRoom>

    /**
     * User State 수정
     * @param id
     * @param state
     */
    @PATCH("/user/manager/state")
    suspend fun updateState(@Query("id") id : Int, @Query("state") state : Int) : Response<User>

    /**
     * state가 0인 User 조회
     */
    @GET("/user/manager/user-state-zero")
    suspend fun selectStateZeroUser() : Response<MutableList<User>>

    /**
     * user Device Token 변경
     */
    @PATCH("user/manager/update-device-token")
    suspend fun updateUserDeviceToken(@Query("id") userId: Int, @Query("token") token: String) : Response<User>

    /**
     * user 이름, auth 조회
     * */
    @GET("user/manager/{userId}")
    suspend fun getUserStatus(@Path("userId") userId: Int) : Response<UserAuthResponse>

}