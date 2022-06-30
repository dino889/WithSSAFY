package com.ssafy.withssafy.src.network.service

import com.ssafy.withssafy.src.dto.report.ReportRequest
import com.ssafy.withssafy.util.RetrofitUtil

class ReportService {

    suspend fun getReportList() = RetrofitUtil.reportApi.selectReportList()

    suspend fun addReport(report: ReportRequest) = RetrofitUtil.reportApi.insertReport(report)

    suspend fun deleteReport(reportId: Int) = RetrofitUtil.reportApi.deleteReport(reportId)

    suspend fun getUserListByBoardId(boardId: Int) = RetrofitUtil.reportApi.selectUserListByBoardId(boardId)

    suspend fun getUserListByCommentId(commentId: Int) = RetrofitUtil.reportApi.selectUserListByCommentId(commentId)
}