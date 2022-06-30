package com.ssafy.withssafy.src.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.withssafy.src.dto.study.Study
import androidx.lifecycle.viewModelScope
import com.ssafy.withssafy.src.dto.board.Comment
import com.ssafy.withssafy.src.dto.study.Team
import com.ssafy.withssafy.src.network.service.StudyService
import kotlinx.coroutines.launch

private const val TAG = "TeamViewModel"
class TeamViewModel : ViewModel(){
    lateinit var uploadedImage: Bitmap
    var uploadImageUri: Uri? = null

//classification  0 => common 1 => special 2=> free
    private val _studyList = MutableLiveData<MutableList<Study>>()
    private val _study = MutableLiveData<Study>()
    private val _studyCommentList = MutableLiveData<MutableList<Comment>>()
    private val _studyCommentParentList = MutableLiveData<MutableList<Comment>>()
    private val _teamBuildList = MutableLiveData<MutableList<Study>>()
    private val _teamInfo = MutableLiveData<Team>()
    var flag = false
    var count = 0;
    var classificationType = -1;
    var minPeople = 0;
    var maxPeople = 0;
    var option:String? = null
    private val peopleText : ObservableField<String> = ObservableField("0")


    val studyList:LiveData<MutableList<Study>>
        get() = _studyList
    val study : LiveData<Study>
        get() = _study
    val studyComments : LiveData<MutableList<Comment>>
        get() = _studyCommentList
    val studyParentComments : LiveData<MutableList<Comment>>
        get() = _studyCommentParentList
    val teamBuildList : LiveData<MutableList<Study>>
        get() = _teamBuildList
    val teamInfo : LiveData<Team>
        get() = _teamInfo

    fun setStudyList(list: MutableList<Study>){
        _studyList.value = list
    }
    fun setStudy(data: Study){
        _study.value = data
    }
    fun setStudyCommentList(list:MutableList<Comment>){
        _studyCommentList.value = list
    }
    fun setStudyCommentParentList(list:MutableList<Comment>){
        _studyCommentParentList.value = list
    }
    fun setTeamBuildList(list:MutableList<Study>){
        _teamBuildList.value = list
    }
    fun setTeamInfo(team:Team){
        _teamInfo.value = team
    }
    fun getButtonText() : ObservableField<String>{
        return peopleText
    }
    fun updateButtonText() {
        peopleText.set(count.toString());
    }
    fun onButtonAddClick(){
        if(!flag){
            ++count;
        }else{
            if(count < maxPeople){
                ++count
            }
        }

        updateButtonText()
    }
    fun onButtonMinusClick(){
        if(!flag){
            if(count > 0){
                --count;
                updateButtonText()
            }else{
                count = 0
                updateButtonText()
            }
        }else{
            if(count > minPeople){
                --count
                updateButtonText()
            }
        }

    }
    suspend fun getStudys(){
        val response = StudyService().getStudys()
        viewModelScope.launch {
            val res = response.body()
            if(response.code() == 200){
                if(res!=null){
                    Log.d(TAG, "getStudys: $res")
                    setStudyList(res)
                }
            }
        }
    }
    suspend fun getStudy(id:Int){
        val response = StudyService().getStudyById(id)
        viewModelScope.launch {
            val res = response.body()
            if(response.code() == 200){
                if(res!=null){
                    setStudy(res)
                }
            }
        }
    }
    suspend fun getStudyCommentByBoardId(boardId:Int) {
        val response = StudyService().getStudyCommentByBoardId(boardId)
        viewModelScope.launch {
            val res = response.body()
            if(response.code() == 200){
                if(res!=null){
                    val commentList = res
                    setStudyCommentList(res)

                    val replyList = mutableListOf<Comment>()
                    for(cmt in commentList){
                        if(cmt.parentId == null || cmt.parentId.toString() == "null"){
                            replyList.add(cmt)
                        }
                    }
                    setStudyCommentParentList(replyList)
                }
            }
        }
    }
    suspend fun getTeamBuildListByRoomId(roomId:Int){
        val response = StudyService().getTeamBuildListByRoomId(roomId)
        viewModelScope.launch {
            val res = response.body()
            if(response.code() == 200){
                if(res!=null){
                    setTeamBuildList(res)
                }
            }
        }
    }

    suspend fun getTeamInfo(){
        val response = StudyService().getTeamInfo()
        viewModelScope.launch {
            val res = response.body()
            if(response.code() == 200){
                if(res!=null){
                    setTeamInfo(res)
                }
            }
        }
    }
}