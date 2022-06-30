package com.ssafy.withssafy.src.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.withssafy.src.dto.Message
import com.ssafy.withssafy.src.network.service.MessageService
import kotlinx.coroutines.launch

class MessageViewModel : ViewModel(){
    private val _messageReceive = MutableLiveData<MutableList<Message>>()
    private val _messageSend = MutableLiveData<MutableList<Message>>()
    private val _messageGroup = MutableLiveData<MutableList<Message>>()
    private val _messageTalk = MutableLiveData<MutableList<Message>>()
    private val _joinList = MutableLiveData<MutableList<Long>>()

    val messageReceive : LiveData<MutableList<Message>>
        get() = _messageReceive
    val messageSend : LiveData<MutableList<Message>>
        get() = _messageSend
    val messageGroup : LiveData<MutableList<Message>>
        get() = _messageGroup
    val messageTalk : LiveData<MutableList<Message>>
        get() = _messageTalk
    val joinList : LiveData<MutableList<Long>>
        get() = _joinList

    fun setMessageReceive(list : MutableList<Message>){
        _messageReceive.value = list
    }
    fun setMessageSend(list:MutableList<Message>){
        _messageSend.value = list
    }
    fun setMessageGroup(list:MutableList<Message>){
        _messageGroup.value = list
    }
    fun setMessageTalk(list:MutableList<Message>){
        _messageTalk.value = list
    }
    fun setJoinList(list:MutableList<Long>){
        _joinList.value = list
    }

    suspend fun getReceiveMessage(userId:Int){
        val response = MessageService().getMessageByUserIdToReceive(userId)
        viewModelScope.launch {
            val res = response.body()
            if(response.code() == 200){
                if(res!=null){
                    setMessageReceive(res)
                }
            }
        }
    }

    suspend fun getSendMessage(userId:Int){
        val response = MessageService().getMessageByUserIdToSend(userId)
        viewModelScope.launch {
            val res = response.body()
            if(response.code() == 200){
                if(res!=null){
                    setMessageSend(res)
                }
            }
        }
    }
    suspend fun getMessageGroup(userId:Int){
        val response = MessageService().getMessageByUserIdToGroup(userId)
        viewModelScope.launch {
            val res = response.body()
            if(response.code() == 200){
                if(res!=null){
                    setMessageGroup(res)
                }
            }
        }
    }

    suspend fun getMessageTalk(toId:Int, fromId:Int){
        val response = MessageService().getMessageTalk(fromId, toId)
        viewModelScope.launch {
            val res = response.body()
            if(response.code() == 200){
                if(res!=null){
                    setMessageTalk(res)
                }
            }
        }
    }

    suspend fun getJoinList(id1:Int,id2:Int){
        val response = MessageService().getStudyJoinList(id1,id2)
        viewModelScope.launch {
            val res = response.body()
            if(response.code() == 200){
                if(res!=null){
                    setJoinList(res)
                }
            }
        }
    }
}