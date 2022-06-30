package com.ssafy.withssafy.src.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.withssafy.src.dto.ClassRoom
import com.ssafy.withssafy.src.dto.User
import com.ssafy.withssafy.src.network.response.UserAuthResponse
import com.ssafy.withssafy.src.network.service.NoticeService
import com.ssafy.withssafy.src.network.service.UserService
import kotlinx.coroutines.launch
import retrofit2.HttpException

class UserViewModel : ViewModel() {
    private val TAG = "UserViewModel_싸피"
    /**
     * @author Jiwoo
     * USER ViewModel
     */
    private val _allUserList = MutableLiveData<MutableList<User>>()
    private val _loginUserInfo = MutableLiveData<User>()
    private val _classRoomList = MutableLiveData<MutableList<ClassRoom>>()
    private val _isAutoLoginPossible = MutableLiveData<Int>()
    private val _classRoomInfo = MutableLiveData<ClassRoom>()
    private val _userInfo = MutableLiveData<User>()
    private val _stateZeroUserList = MutableLiveData<MutableList<User>>()
    private val _userInfoAuth = MutableLiveData<UserAuthResponse>()

    val allUserList : LiveData<MutableList<User>>
        get() = _allUserList

    val loginUserInfo : LiveData<User>
        get() = _loginUserInfo

    val classRommList : LiveData<MutableList<ClassRoom>>
        get() = _classRoomList

    val isAutoLoginPossible : LiveData<Int>
        get() = _isAutoLoginPossible

    val classRoomInfo : LiveData<ClassRoom>
        get() = _classRoomInfo

    val userInfo : LiveData<User>
        get() = _userInfo

    val stateZeroUserList : LiveData<MutableList<User>>
        get() = _stateZeroUserList

    val userInfoAuth : LiveData<UserAuthResponse>
        get() = _userInfoAuth

    private fun setAllUserList(userList : MutableList<User>) = viewModelScope.launch {
        _allUserList.value = userList
    }
    private fun setUserInfo(user:User) = viewModelScope.launch {
        _userInfo.value = user
    }
    private fun setLoginUserInfo(user: User) = viewModelScope.launch {
        _loginUserInfo.value = user
    }

    private fun setClassRoomList(classRoomList : MutableList<ClassRoom>) = viewModelScope.launch {
        _classRoomList.value = classRoomList
    }

    private fun setIsAutoLoginPossible(possible : Int) {
        _isAutoLoginPossible.value = possible
    }

    private fun setClassRoomInfo(classRoom : ClassRoom) = viewModelScope.launch{
        _classRoomInfo.value = classRoom
    }

    private fun setStateZeroUserList(userList: MutableList<User>) = viewModelScope.launch {
        _stateZeroUserList.value = userList
    }

    private fun setUserInfoAuth(userInfoAuth : UserAuthResponse) = viewModelScope.launch {
        _userInfoAuth.value = userInfoAuth
    }

    suspend fun getAllUserList() {
        val response = UserService().getUserList()
        Log.d(TAG, "getAllUserList: $response")
        viewModelScope.launch {

//
//            if(response.code() == 200 || response.code() == 500) {
//                val res = response.body()
//                if(res != null) {
//                    if(res.success) {
//                        if(res.data["user"] != null && res.message == "회원 정보 조회 성공") {
//                            val type = object : TypeToken<MutableList<User>>() {}.type
//                            val userList: MutableList<User> = CommonUtils.parseDto(res.data["user"]!!, type)
//                            setAllUserList(userList)
//                        } else {
//                            Log.e(TAG, "getAllUserList: ${res.message}", )  // 회원 정보 조회 실패
//                        }
//                    } else {
//                        Log.e(TAG, "getAllUserList: 서버 통신 실패 ${res.message}", )
//                    }
//                }
//            }
        }
    }

    suspend fun getClassRoomList() {
        val response = UserService().getClassRoomList()
        viewModelScope.launch {
            var res = response.body()
            if(response.code() == 200) {
                if(res != null) {
                    setClassRoomList(res)
                    Log.d(TAG, "getClassRoomList: $res")
                }
            } else {
                Log.d(TAG, "Error : ${response.message()}")
            }
        }
    }

    suspend fun getUser(userId: Int,flag:Int){
        val response = UserService().getUser(userId)
        viewModelScope.launch {
            val res = response.body()
            Log.d(TAG, "getUser: $userId $res")
            if(response.code() == 200) {
                if(res != null) {
                    Log.d(TAG, "getUser: $res")
                    if(flag==1){
                        setIsAutoLoginPossible(1)
                        setLoginUserInfo(res)
                    }else if(flag==2){
                        setUserInfo(res)
                    }

                } else {
                    setIsAutoLoginPossible(0)
                }
            }
        }
    }

    suspend fun getClassRoom(userId: Int){
        val response = UserService().getUser(userId)
        viewModelScope.launch {
            val res = response.body()
            if(response.code() == 200) {
                if(res != null) {
                    setLoginUserInfo(res)
                    val responseClassRoom = UserService().getClassRoom(res.classRoomId)
                    val resClassRoom = responseClassRoom.body()
                    if(responseClassRoom.code() == 200) {
                        if(resClassRoom != null) {
                            setClassRoomInfo(resClassRoom)
                            Log.d(TAG, "getClassRoomInfo: $resClassRoom")
                        }
                    } else {
                        Log.d(TAG, "Error : ${response.message()}")
                    }
                }
            } else {
                Log.d(TAG, "Error : ${response.message()}")
            }
        }
    }

    suspend fun getStateZeroUserList() {
        val response = UserService().selectStateZeroUser()
        viewModelScope.launch {
            var res = response.body()
            if(response.code() == 200) {
                if(res != null) {
                    var studentList = mutableListOf<User>()
                    res.forEach {
                        if(it.studentId != null) {
                            studentList.add(it)
                        }
                    }
                    setStateZeroUserList(studentList)
                    Log.d(TAG, "getStateZeroUserList: $res")
                }
            } else {
                Log.d(TAG, "Error : ${response.message()}")
            }
        }
    }

    suspend fun getClassRoomInfo(id : Int) {
        val response = UserService().getClassRoom(id)
        viewModelScope.launch {
            val res = response.body()
            if(response.code() == 200) {
                if(res != null) {
                    setClassRoomInfo(res)
                }
            }
        }
    }

    suspend fun getUserInfoAuth(userId: Int) {
        try {
            val response = UserService().getUserStatus(userId)
            viewModelScope.launch {
                var res = response.body()
                if (response.code() == 200) {
                    if (res != null) {
                        setUserInfoAuth(res)
                        Log.d(TAG, "getUserInfoAuth: $res")
                    }
                } else {
                    Log.d(TAG, "Error : ${response.message()}")
                }
            }
        } catch (e: HttpException) {
            Log.e(TAG, "getAllList: ${e.message()}", )
        }
    }

}