package com.ssafy.withssafy.src.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.withssafy.src.dto.board.Board
import com.ssafy.withssafy.src.dto.report.Report
import com.ssafy.withssafy.src.network.service.BoardService
import com.ssafy.withssafy.src.network.service.ReportService
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HomeViewModel : ViewModel(){
    private val TAG = "HomeViewModel_ws"
    private val _bannerItemList: MutableLiveData<List<Int>> = MutableLiveData()
    private val _currentPosition: MutableLiveData<Int> = MutableLiveData()

    val bannerItemList: LiveData<List<Int>>
        get() = _bannerItemList

    val currentPosition: LiveData<Int>
        get() = _currentPosition

    init{
        _currentPosition.value = 0
    }

    fun setBannerItems(list: List<Int>) {
        _bannerItemList.value = list
    }

    fun setCurrentPosition(position: Int) {
        _currentPosition.value = position
    }

    fun getcurrentPosition() = currentPosition.value


    /**
     * 신고 내용 조회
     */
    private val _reportList = MutableLiveData<MutableList<Report>>()

    val reportList : LiveData<MutableList<Report>>
        get() = _reportList

    private fun setReportList(list: MutableList<Report>) = viewModelScope.launch {
        _reportList.value = list
    }

    suspend fun getReportList() {
        try {
            val response = ReportService().getReportList()

            viewModelScope.launch {
                if(response.isSuccessful) {
                    val res = response.body()
                    if(res != null) {
                        setReportList(res as MutableList<Report>)
                    } else {
                        Log.d(TAG, "getReportList: $response", )
                    }
                } else {
                    Log.e(TAG, "getReportList: 통신 실패", )
                }
            }
        } catch (e: HttpException) {
            Log.e(TAG, "getReportList ${e.message()}", )
        }
    }
}