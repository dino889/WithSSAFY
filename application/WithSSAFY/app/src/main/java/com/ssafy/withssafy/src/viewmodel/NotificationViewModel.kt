package com.ssafy.withssafy.src.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.withssafy.src.dto.Notification
import com.ssafy.withssafy.src.network.service.NotificationService
import kotlinx.coroutines.launch

class NotificationViewModel : ViewModel() {
    private val _notiList = MutableLiveData<MutableList<Notification>>()
    private val _notiListByType = MutableLiveData<MutableList<Notification>>()

    val notiList : LiveData<MutableList<Notification>>
        get() = _notiList

    val notiListByType : LiveData<MutableList<Notification>>
        get() = _notiListByType

    fun setNotiList(notiList : MutableList<Notification>) {
        _notiList.value = notiList
    }

    fun setNotiListByType(notiList : MutableList<Notification>) {
        _notiListByType.value = notiList
    }

    suspend fun getNotiList(userId : Int) {
        val response = NotificationService().selectNotificationByUser(userId)
        viewModelScope.launch {
            val res = response.body()
            if(response.code() == 200) {
                if(res != null) {
                    setNotiList(res)
                }
            }
        }
    }

    suspend fun getNotiListByType(type : Int, userId : Int) {
        val response = NotificationService().selectNotificationByType(type, userId)
        viewModelScope.launch {
            val res = response.body()
            if(response.code() == 200) {
                if(res != null) {
                    setNotiList(res)
                }
            }
        }
    }
}