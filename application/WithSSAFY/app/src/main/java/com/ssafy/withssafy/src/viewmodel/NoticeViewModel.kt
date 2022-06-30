package com.ssafy.withssafy.src.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.withssafy.src.dto.notice.Notice
import com.ssafy.withssafy.src.network.service.NoticeService
import kotlinx.coroutines.launch

private const val TAG = "NoticeViewModel"
class NoticeViewModel : ViewModel() {
    private val _noticeAllList = MutableLiveData<MutableList<Notice>>()
    private val _classRoomId = MutableLiveData<Int>()
    var _uploadImageUri = MutableLiveData<Uri?>()
    private val _noticeFilterList = MutableLiveData<MutableList<Notice>>()
    private val _notice = MutableLiveData<Notice>()
    private val _classNoticeList = MutableLiveData<MutableList<Notice>>()

    val noticeAllList : LiveData<MutableList<Notice>>
        get() = _noticeAllList

    val classRoomId : LiveData<Int>
        get() = _classRoomId

    val uploadImageUri : LiveData<Uri?>
        get() = _uploadImageUri

    val noticeFilterList : LiveData<MutableList<Notice>>
        get() = _noticeFilterList

    val notice : LiveData<Notice>
        get() = _notice

    val classNoticeList : LiveData<MutableList<Notice>>
        get() = _classNoticeList

    fun setNoticeList(noticeList : MutableList<Notice>) {
        _noticeAllList.value = noticeList
    }

    fun setClassRoomId(id : Int) {
        _classRoomId.value = id
    }

    fun setUploadImageUri(uri: Uri?) {
        _uploadImageUri.value = uri
    }

    fun setNoticeFilterListDefault() {
        _noticeFilterList.value = _noticeAllList.value
    }
    fun setNoticeFilterList(noticeList : MutableList<Notice>) {
        _noticeFilterList.value = noticeList
    }

    fun setNotice(notice: Notice) {
        _notice.value = notice
    }

    fun setClassNoticeList(noticeList : MutableList<Notice>) {
        _classNoticeList.value = noticeList
    }

    suspend fun getNoticeList(classRoomId : Int) {
        val response = NoticeService().selectNoticeList(classRoomId)
        viewModelScope.launch {
            val res = response.body()
            if(response.code() == 200) {
                if(res != null) {
                    setNoticeList(res)
                    Log.d(TAG, "getNoticeList: $classRoomId $res")
                }
            }
        }
    }

    fun getFilterNoticeList(typeId : Int) {
        var nList = mutableListOf<Notice>()
        if(typeId == 0) {
            nList = noticeAllList.value!!
        } else {
            noticeAllList.value!!.forEach {
                if(it.typeId == typeId) nList.add(it)
            }
        }
        Log.d(TAG, "getFilterNoticeList: ${noticeAllList.value}")
        Log.d(TAG, "getFilterNoticeList: $nList")
        setNoticeFilterList(nList)
    }

    suspend fun getNotice(id : Int) {
        val response = NoticeService().selectNoticeById(id)
        viewModelScope.launch {
            val res = response.body()
            if(response.code() == 200) {
                if(res != null) {
                    setNotice(res)
                }
            }
        }
    }

    suspend fun getClassNoticeList(classRoomId: Int) {
        val response = NoticeService().selectNoticeList(classRoomId)
        viewModelScope.launch {
            val res = response.body()
            if(response.code() == 200) {
                if(res != null) {
                    setClassNoticeList(res)
                    Log.d(TAG, "getClassNoticeList: $classRoomId $res")
                }
            }
        }
    }


}