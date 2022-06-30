package com.ssafy.withssafy.src.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.reflect.TypeToken
import com.ssafy.withssafy.src.dto.board.Board
import com.ssafy.withssafy.src.dto.board.BoardType
import com.ssafy.withssafy.src.dto.board.Comment
import com.ssafy.withssafy.src.network.service.BoardService
import com.ssafy.withssafy.src.network.service.CommentService
import com.ssafy.withssafy.util.CommonUtils
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.URI

/**
 * @since 04/26/22
 * @author Jiwoo Choi
 */
class BoardViewModel : ViewModel() {
    private val TAG = "BoardViewModel_싸피"

    /**
     * 사진 추가
     */
    var uploadImageUri: Uri? = null
    private var _boardImgUri = MutableLiveData<Uri>()  // 파일 uri

    val boardImgUri : LiveData<Uri>
        get() = _boardImgUri

    fun setBoardImgUri(uri: Uri) {
        _boardImgUri.value = uri
    }



    /**
     * board type 전체 조회
     */
    private val _allBoardType = MutableLiveData<MutableList<BoardType>>()

    val allBoardType : LiveData<MutableList<BoardType>>
        get() = _allBoardType

    private fun setAllBoardType(list: MutableList<BoardType>) = viewModelScope.launch {
        _allBoardType.value = list
    }

    suspend fun getAllBoardType() {
        try {
            val response = BoardService().getAllBoardTType()
            viewModelScope.launch {
                if (response.isSuccessful) {
                    val res = response.body()
                    if (res != null) {
                        setAllBoardType(res as MutableList<BoardType>)
                    } else {
                        Log.e(TAG, "getAllBoardType: $response", )
                    }
                } else {
                    Log.e(TAG, "getAllBoardType: 통신 실패", )
                }
            }
        } catch (e: HttpException) {
            Log.e(TAG, "getAllBoardType: ${e.message()}", )
        }
    }

    /**
     * board Table 전체 조회
     */
    private val _allList = MutableLiveData<MutableList<Board>>()

    val allList : LiveData<MutableList<Board>>
        get() = _allList
    
    private fun setAllList(list: MutableList<Board>) = viewModelScope.launch {
        _allList.value = list
    }
    
    suspend fun getAllList() {
        try {
            val response = BoardService().getAllList()
            
            viewModelScope.launch { 
                if(response.isSuccessful) {
                    val res = response.body()
                    if(res != null) {
                        setAllList(res as MutableList<Board>)
                    } else {
                        Log.e(TAG, "getAllList: ", )
                    }
                } else {
                    Log.e(TAG, "getAllList: 통신 실패", )
                }
            }
        } catch (e: HttpException) {
            Log.e(TAG, "getAllList: ${e.message()}", )
        }
    }

    /**
     * 게시글 상세 조회
     */
    private val _postDetail = MutableLiveData<Board>()

    val postDetail : LiveData<Board>
        get() = _postDetail

    private fun setPostDetail(post: Board) = viewModelScope.launch {
        _postDetail.value = post
    }

    suspend fun getPostDetail(postId: Int) {
        try {
            val response = BoardService().getPostDetail(postId)

            viewModelScope.launch {
                if(response.isSuccessful) {
                    val res = response.body()
                    if(res != null) {
                        setPostDetail(res)
                    } else {
                        Log.e(TAG, "getAllList: ", )
                    }
                } else {
                    Log.e(TAG, "getAllList: 통신 실패", )
                }
            }
        } catch (e: HttpException) {
            Log.e(TAG, "getAllList: ${e.message()}", )
        }
    }


    /**
     * 게시글에 해당하는 댓글, 대댓글 리스트
     */
    private val _commentListOnPost = MutableLiveData<MutableList<Comment>>()
    private val _commentList = MutableLiveData<MutableList<Comment>>()

    val commentListOnPost : LiveData<MutableList<Comment>>  // 게시글에 대한 댓글 전체
        get() = _commentListOnPost

    val commentList : LiveData<MutableList<Comment>>    // 댓글 리스트(대댓글 제외)
        get() = _commentList

    private fun setCommentListOnPost(commentList : MutableList<Comment>) = viewModelScope.launch {
        _commentListOnPost.value = commentList
    }

    private fun setCommentList(commentList: MutableList<Comment>) = viewModelScope.launch {
        _commentList.value = commentList
    }

    suspend fun getCommentList(postId : Int) {
        val response = CommentService().getCommentListByBoardId(postId)

        viewModelScope.launch {
            if(response.isSuccessful) {
                val res = response.body()
                if(res != null) {
                    val commentList = res as MutableList<Comment>
                    setCommentListOnPost(commentList)

                    val replyList = mutableListOf<Comment>()
                    for(cmt in commentList) {
                        if(cmt.parentId == null || cmt.parentId.toString() == "null") {
                            replyList.add(cmt)
                        }
                    }
                    setCommentList(replyList)

                }
            }
        }
    }

    /**
     * 게시판 타입별 게시글 리스트 조회
     */
    private val _boardListByType = MutableLiveData<MutableList<Board>>()

    val boardListByType : LiveData<MutableList<Board>>
        get() = _boardListByType

    private fun setBoardListByType(list: MutableList<Board>) = viewModelScope.launch {
        _boardListByType.value = list
    }

    suspend fun getBoardListByType(typeId: Int) {
        try {
            val response = BoardService().getBoardListByTypeId(typeId)

            viewModelScope.launch {
                if(response.isSuccessful) {
                    val res = response.body()
                    if(res != null) {
                        setBoardListByType(res as MutableList<Board>)
                    } else {
                        Log.e(TAG, "getBoardListByType: $response", )
                    }
                } else {
                    Log.e(TAG, "getBoardListByType: 통신 실패", )
                }
            }
        } catch (e: HttpException) {
            Log.e(TAG, "getBoardListByType: ${e.message()}", )
        }
    }


    /**
     * 게시글 좋아요 좋아요 체크
     */
    private val _likePost = MutableLiveData<Boolean>()

    val likePost : LiveData<Boolean>
        get() = _likePost

    private fun setLikePost(value: Boolean) = viewModelScope.launch {
        _likePost.value = value
    }

    suspend fun getLikePostOrNot(boardId: Int, userId: Int) {
        try {
            val response = BoardService().postLikeOrNot(boardId, userId)

            viewModelScope.launch {
                if(response.isSuccessful) {
                    val res = response.body()
                    if(res != null) {
                        setLikePost(res)
                    } else {
                        Log.d(TAG, "getLikePostOrNot: $response", )
                    }
                } else {
                    Log.e(TAG, "getLikePostOrNot: 통신 실패", )
                }
            }
        } catch (e: HttpException) {
            Log.e(TAG, "getLikePostOrNot: ${e.message()}", )
        }
    }


    /**
     * 사용자가 좋아요 누른 게시글 리스트 조회
     */
    private val _userLikePostList = MutableLiveData<MutableList<Board>>()

    val userLikePostList : LiveData<MutableList<Board>>
     get() = _userLikePostList

    private fun setUserLikePost(list: MutableList<Board>) = viewModelScope.launch {
        _userLikePostList.value = list
    }

    suspend fun getUserLikePostList(userId: Int) {
        try {
            val response = BoardService().likePostByUser(userId)

            viewModelScope.launch {
                if(response.isSuccessful) {
                    val res = response.body()
                    if(res != null) {
                        setUserLikePost(res as MutableList<Board>)
                    } else {
                        Log.d(TAG, "getUserLikePostList: $response", )
                    }
                } else {
                    Log.e(TAG, "getUserLikePostList: 통신 실패", )
                }
            }
        } catch (e: HttpException) {
            Log.e(TAG, "getUserLikePostList ${e.message()}", )
        }
    }


    /**
     * Hot 게시글 조회
     */
    private val _hotPostList = MutableLiveData<MutableList<Board>>()

    val hotPostList : LiveData<MutableList<Board>>
        get() = _hotPostList

    private fun setHotPost(list: MutableList<Board>) = viewModelScope.launch {
        _hotPostList.value = list
    }

    suspend fun getHotPostList() {
        try {
            val response = BoardService().getHotPostList()

            viewModelScope.launch {
                if(response.isSuccessful) {
                    val res = response.body()
                    if(res != null) {
                        setHotPost(res as MutableList<Board>)
                    } else {
                        Log.d(TAG, "getHotPostList: $response", )
                    }
                } else {
                    Log.e(TAG, "getHotPostList: 통신 실패", )
                }
            }
        } catch (e: HttpException) {
            Log.e(TAG, "getHotPostList ${e.message()}", )
        }
    }


    /**
     * 사용자가 작성한 게시글 리스트 조회
     */
    private val _userWrotePostList = MutableLiveData<MutableList<Board>>()

    val userWrotePostList : LiveData<MutableList<Board>>
        get() = _userWrotePostList

    private fun setUserWrotePost(list: MutableList<Board>) = viewModelScope.launch {
        _userWrotePostList.value = list
    }

    suspend fun getUserWrotePostList(userId: Int) {
        try {
            val response = BoardService().getBoardListByUserId(userId)

            viewModelScope.launch {
                if(response.isSuccessful) {
                    val res = response.body()
                    if(res != null) {
                        setUserWrotePost(res as MutableList<Board>)
                    } else {
                        Log.d(TAG, "getUserWrotePostList: $response", )
                    }
                } else {
                    Log.e(TAG, "getUserWrotePostList: 통신 실패", )
                }
            }
        } catch (e: HttpException) {
            Log.e(TAG, "getUserWrotePostList ${e.message()}", )
        }
    }



    /**
     * 사용자가 댓글 단 게시글 리스트 조회
     */
    private val _userPostListOnComment = MutableLiveData<MutableList<Board>>()

    val userPostListOnComment : LiveData<MutableList<Board>>
        get() = _userPostListOnComment

    private fun setUserPostListOnComment(list: MutableList<Board>) = viewModelScope.launch {
        _userPostListOnComment.value = list
    }

    suspend fun getUserPostListOnComment(userId: Int) {
        try {
            val response = BoardService().getPostInAComment(userId)

            viewModelScope.launch {
                if(response.isSuccessful) {
                    val res = response.body()
                    if(res != null) {
                        setUserPostListOnComment(res as MutableList<Board>)
                    } else {
                        Log.d(TAG, "getUserPostListOnComment: $response", )
                    }
                } else {
                    Log.e(TAG, "getUserPostListOnComment: 통신 실패", )
                }
            }
        } catch (e: HttpException) {
            Log.e(TAG, "getUserPostListOnComment ${e.message()}", )
        }
    }
}