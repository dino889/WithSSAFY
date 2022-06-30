package com.ssafy.withssafy.src.network.api

import com.ssafy.withssafy.src.dto.board.Comment
import com.ssafy.withssafy.src.dto.study.Study
import com.ssafy.withssafy.src.dto.study.StudyComment
import com.ssafy.withssafy.src.dto.study.StudyMemberRequest
import com.ssafy.withssafy.src.dto.study.Team
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface StudyApi {
    /**
     * Study Get All
     * @author : LeeBoYeon
     * 모든 스터디목록 불러오기
     */
    @GET("/study-boards/study")
    suspend fun getStudys() : Response<MutableList<Study>>
    /**
     * Study Update
     * @author : LeeBoYeon
     * 스터디 수정하기
     * */
    @PUT("/study-boards/{id}")
    suspend fun updateStudy(@Path("id")id:Int, @Body study:Study) : Response<Any?>
    /**
     * Study insert
     * @author : LeeBoYeon
     * 스터디 추가하기
     * */
    @POST("/study-boards")
    suspend fun insertStudy(@Body studyBoardRequest: Study) : Response<Any?>
    /**
     * Study Photo insert
     * @author : LeeBoYeon
     * 스터디 사진 추가하기
     * */
    @Multipart
    @POST("/upload")
    suspend fun insertPhoto(@Part file:MultipartBody.Part) : Response<String>
    /**
     * Study Get Detail
     * @author : LeeBoYeon
     * 스터디 ID 값으로 상세정보 불러오기
     * @param : id
     * */
    @GET("/study-boards/{id}")
    suspend fun getStudyById(@Path("id") id:Int):Response<Study>

    /**
     * Study Delete Detail
     * @author : LeeBoYeon
     * 스터디 ID 값으로 스터디게시물 삭제하기
     * @param : id
     * */
    @DELETE("/study-boards/{id}")
    suspend fun deleteStudy(@Path("id")id:Int) : Response<Any?>
    /**
     * Study Join Request
     * @author : LeeBoYeon
     * 스터디 지원하기
     * @param : id, studyMemberRequest
     * */
    @POST("/study-boards/{id}/join")
    suspend fun joinStudy(@Path("id")id:Int, @Body studyMemberRequest:StudyMemberRequest) : Response<Any?>

    /**
     * Study Join Remove
     * @author : LeeBoYeon
     * 스터디 지원 취소하기
     * @param : id, studyMemberRequest
     * */
    @DELETE("/study-boards/{id}/leave")
    suspend fun joinStudyRemove(@Path("id")id:Int, @Body studyMemberRequest: StudyMemberRequest) : Response<Any?>

    /**
     * Study Comment GET
     * @author : LeeBoYeon
     * 스터디의 모든 댓글 조회
     * */
    @GET("/sb-comment")
    suspend fun getAllStudyComment() : Response<MutableList<Comment>>

    /**
     * Study Comment Insert
     * @author : LeeBoYeon
     * 스터디 게시물에 댓글달기
     * */
    @POST("/sb-comment")
    suspend fun insertStudyComment(@Body studyComment:Comment) : Response<Any?>

    /**
     * Study Comment Modify
     * @author : LeeBoYeon
     * 본인이 단 댓글 수정하기
     * */
    @PUT("/sb-comment")
    suspend fun modifyStudyComment(@Body studyComment: Comment) : Response<Any?>

    /**
     * Study Comment Delete
     * @author : LeeBoYeon
     * 본인이 단 댓글 삭제하기
     * */
    @DELETE("/sb-comment")
    suspend fun deleteStudyComment(@Query("id")id:Int) : Response<Any?>

    /**
     * StudyComment Get By BoardId
     * 한 게시물에 해당하는 스터디 댓글 가져오기
     * @author : LeeBoYeon
     * */
    @GET("/sb-comment/{boardId}")
    suspend fun getStudyCommentByBoardId(@Path("boardId")boardId:Int) : Response<MutableList<Comment>>

    /**
     * StudyComment Get By UserId
     * 사용자가 작성한 댓글목록 가져오기
     * @author : LeeBoYeon
     */
    @GET("/sb-comment/user/{userId}")
    suspend fun getStudyCommentByUserId(@Path("userId")userId:Int) : Response<MutableList<Comment>>

    /**
     * TeamBuilding Get By roomId
     * type이 1인 나의 지역 팀빌딩리스트 가져오기
     * @author : LeeBoYeon
     * */
    @GET("/study-boards/team-building")
    suspend fun getTeamBuildListByRoomId(@Query("classRoomId")classRoomId:Int) : Response<MutableList<Study>>

    /**
     * Team 정보 입력하기
     * @author : LeeBoYeon
     * */
    @PUT("/team")
    suspend fun insertTeamInfo(@Body teamDto:Team) : Response<Any?>

    /**
     * Team 마지막 컬럼 가져오기
     * @author : LeeBoYeon
     * */
    @GET("/team/last")
    suspend fun getTeamInfo() : Response<Team>


}