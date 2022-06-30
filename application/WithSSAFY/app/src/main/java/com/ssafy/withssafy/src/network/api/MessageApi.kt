package com.ssafy.withssafy.src.network.api

import com.ssafy.withssafy.src.dto.Message
import retrofit2.Response
import retrofit2.http.*

interface MessageApi {
    /**
     * Message All Get
     * 모든 메시지를 가져온다
     * @author : LeeBoYeon
     * */
    @GET("/message")
    suspend fun getAllMessage() : Response<MutableList<Message>>

    /**
     * Message Insert
     * 메시지를 보낸다
     * @author : LeeBoYeon
     * */
    @POST("/message")
    suspend fun insertMessage(@Body messageDto:Message) : Response<Any?>

    /**
     * Message Get By UserId to Receive
     * 유저아이디에 해당하는 받은 메시지들을 불러온다
     * @author : LeeBoYeon
     * */
    @GET("/message/receive")
    suspend fun getMessageByUserIdToReceive(@Query("id")id:Int) : Response<MutableList<Message>>

    /**
     * Message Get By UserId to Send
     * 유저아이디에 해당하는 보낸 메시지들을 불러온다
     * @author : LeeBoYeon
     * */
    @GET("/message/send")
    suspend fun getMessageByUserIdToSend(@Query("id")id:Int) : Response<MutableList<Message>>

    /**
     * Message Get Group By userId
     * 내 메시지 목록 불러오기
     * @author : LeeBoYeon
     * */
    @GET("/message/list/{id}")
    suspend fun getMessageByUserIdToGroup(@Path("id") id:Int) : Response<MutableList<Message>>

    /**
     * Message Get Talk us
     * 상대방과 메시지한 목록 불러오기
     * @author : LeeBoYeon
     * */
    @GET("/message/list")
    suspend fun getMessageTalk(@Query("fromId")fromId:Int, @Query("toId")toId:Int) : Response<MutableList<Message>>

    /**
     * Message Get Study
     * 상대방과 내가 같이 들어가있는 스터디목록 가져오기
     * @author : LeeBoYeon
     * */
    @GET("/message/study/list")
    suspend fun getStudyJoinList(@Query("id1")id1 : Int, @Query("id2")id2 : Int) : Response<MutableList<Long>>

    /**
     * Message Group delete
     * 메시지를 삭제한다 (상대방 나 모두)
     * @author : LeeBoYeon
     * */
    @DELETE("/message")
    suspend fun deleteGroupMsg(@Query("id1")id1:Int, @Query("id2")id2:Int) : Response<Any?>

    /**
     * Message Detail delete
     * 메시지를 삭제한다 ( 개개인 )
     * @author : LeeBoYeon
     * */
    @DELETE("/message/{id}")
    suspend fun deleteDetailMsg(@Path("id") id: Int) : Response<Any?>
}

