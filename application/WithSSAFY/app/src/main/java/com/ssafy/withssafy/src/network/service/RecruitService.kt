package com.ssafy.withssafy.src.network.service

import com.ssafy.withssafy.src.dto.Recruit
import com.ssafy.withssafy.src.dto.RecruitLike
import com.ssafy.withssafy.util.RetrofitUtil
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.Query

class RecruitService {
    suspend fun selectRecruitAll() = RetrofitUtil.recruitApi.selectRecruitAll()

    suspend fun insertRecruit(recruitDto : Recruit) = RetrofitUtil.recruitApi.insertRecruit(recruitDto)

    suspend fun updateRecruit(recruitDto : Recruit) = RetrofitUtil.recruitApi.updateRecruit(recruitDto)

    suspend fun selectRecruitById(id: Int) = RetrofitUtil.recruitApi.selectRecruitById(id)

    suspend fun deleteRecruitById(id: Int) = RetrofitUtil.recruitApi.deleteRecruitById(id)

    suspend fun likeAndCancelRecruit(recruitLikeDto : RecruitLike) = RetrofitUtil.recruitApi.likeAndCancelRecruit(recruitLikeDto)

    suspend fun isLikeRecruit(recruitId: Int, userId: Int) = RetrofitUtil.recruitApi.isLikeRecruit(recruitId, userId)

    suspend fun likeRecruitList(id: Int) = RetrofitUtil.recruitApi.likeRecruitList(id)
}
