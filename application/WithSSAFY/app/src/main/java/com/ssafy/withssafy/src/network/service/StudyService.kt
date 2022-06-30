package com.ssafy.withssafy.src.network.service

import com.ssafy.withssafy.src.dto.board.Comment
import com.ssafy.withssafy.src.dto.study.Study
import com.ssafy.withssafy.src.dto.study.StudyComment
import com.ssafy.withssafy.src.dto.study.StudyMemberRequest
import com.ssafy.withssafy.src.dto.study.Team
import com.ssafy.withssafy.util.RetrofitUtil
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit

class StudyService {
    suspend fun getStudys():Response<MutableList<Study>> = RetrofitUtil.studyService.getStudys()

    suspend fun insertStudy(study:Study): Response<Any?> = RetrofitUtil.studyService.insertStudy(study)

    suspend fun insertPhoto(file:MultipartBody.Part) : Response<String> = RetrofitUtil.studyService.insertPhoto(file)

    suspend fun updateStudy(id:Int, study:Study) : Response<Any?> = RetrofitUtil.studyService.updateStudy(id,study)

    suspend fun getStudyById(id:Int) : Response<Study> = RetrofitUtil.studyService.getStudyById(id)

    suspend fun deleteStudy(id:Int) : Response<Any?> = RetrofitUtil.studyService.deleteStudy(id)

    suspend fun joinStudy(id:Int, studyMemberRequest: StudyMemberRequest) : Response<Any?> = RetrofitUtil.studyService.joinStudy(id,studyMemberRequest)

    suspend fun joinStudyRemove(id:Int, studyMemberRequest: StudyMemberRequest) : Response<Any?> = RetrofitUtil.studyService.joinStudyRemove(id,studyMemberRequest)

    suspend fun getAllStudyComment():Response<MutableList<Comment>> = RetrofitUtil.studyService.getAllStudyComment()

    suspend fun insertStudyComment(studyComment: Comment) : Response<Any?> = RetrofitUtil.studyService.insertStudyComment(studyComment)

    suspend fun modifyStudyComment(studyComment: Comment) : Response<Any?> = RetrofitUtil.studyService.modifyStudyComment(studyComment)

    suspend fun deleteStudyComment(id:Int) : Response<Any?> = RetrofitUtil.studyService.deleteStudyComment(id)

    suspend fun getStudyCommentByBoardId(boardId:Int) : Response<MutableList<Comment>> = RetrofitUtil.studyService.getStudyCommentByBoardId(boardId)

    suspend fun getStudyCommentByUserId(userId:Int) : Response<MutableList<Comment>> = RetrofitUtil.studyService.getStudyCommentByUserId(userId)

    suspend fun getTeamBuildListByRoomId(roomId:Int) : Response<MutableList<Study>> = RetrofitUtil.studyService.getTeamBuildListByRoomId(roomId)

    suspend fun insertTeamInfo(team: Team):Response<Any?> = RetrofitUtil.studyService.insertTeamInfo(team)

    suspend fun getTeamInfo() : Response<Team> = RetrofitUtil.studyService.getTeamInfo()
}