package com.ssafy.withssafy.src.network.service

import com.ssafy.withssafy.src.dto.Message
import com.ssafy.withssafy.util.RetrofitUtil
import retrofit2.Response

class MessageService {

    suspend fun insertMessage(message:Message) : Response<Any?> = RetrofitUtil.messageApi.insertMessage(message)

    suspend fun getMessageByUserIdToReceive(id:Int) : Response<MutableList<Message>> = RetrofitUtil.messageApi.getMessageByUserIdToReceive(id)

    suspend fun getMessageByUserIdToSend(id: Int) : Response<MutableList<Message>> = RetrofitUtil.messageApi.getMessageByUserIdToSend(id)

    suspend fun getMessageByUserIdToGroup(id:Int) : Response<MutableList<Message>> = RetrofitUtil.messageApi.getMessageByUserIdToGroup(id)

    suspend fun getMessageTalk(fromId:Int, toId:Int) : Response<MutableList<Message>> = RetrofitUtil.messageApi.getMessageTalk(fromId, toId)

    suspend fun getStudyJoinList(id1:Int, id2:Int) : Response<MutableList<Long>> = RetrofitUtil.messageApi.getStudyJoinList(id1,id2)

    suspend fun deleteGroupMsg(id1: Int, id2: Int) : Response<Any?> = RetrofitUtil.messageApi.deleteGroupMsg(id1, id2)

    suspend fun deleteDetailMsg(id:Int) : Response<Any?> = RetrofitUtil.messageApi.deleteDetailMsg(id)
}