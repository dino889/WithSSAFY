package com.ssafy.withssafy.src.main.board

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.view.clicks
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentCommentBinding
import com.ssafy.withssafy.src.dto.board.Comment
import com.ssafy.withssafy.src.main.MainActivity
import com.ssafy.withssafy.src.network.service.BoardService
import com.ssafy.withssafy.src.network.service.CommentService
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.http.HTTP
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

/**
 * @since 04/27/22
 * @author Jiwoo Choi
 */
class CommentFragment : BaseFragment<FragmentCommentBinding>(FragmentCommentBinding::bind, R.layout.fragment_comment) {
    private val TAG = "CommentFragment_ws"
    private lateinit var mainActivity: MainActivity

    private lateinit var commentAdapter: CommentAdapter
    private lateinit var mInputMethodManager: InputMethodManager

    // 대댓글 작성 시 필요한 parentId == commentId
    private var parentId = -1

    private val userId = ApplicationClass.sharedPreferencesUtil.getUser().id

    var lastHeightDiff = 0
    var isOpenKeyboard = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    private var postId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            postId = it.getInt("postId")
        }
        mInputMethodManager = mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        mOnGlobalLayoutListener.onGlobalLayout()
    }

    override fun onResume() {
        super.onResume()
        mOnGlobalLayoutListener.onGlobalLayout()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.hideBottomNavi(true)

        runBlocking {
            boardViewModel.getCommentList(postId)
        }

        initListener()
        initRecyclerView()
    }

    private fun initListener() {
        backBtnClickEvent()
        layoutListener()
        insertCommentAndReply() // default : 댓글 등록
    }

    /**
     * 뒤로가기 버튼 클릭 이벤트
     */
    private fun backBtnClickEvent() {
        binding.commentFragmentIbBack.setOnClickListener {
            this@CommentFragment.findNavController().popBackStack()
        }
    }

    /**
     * 댓글 조회 레이아웃에 키보드 감지 리스너 등록
     */
    private fun layoutListener() {
        binding.commentFragment.viewTreeObserver.addOnGlobalLayoutListener(
            mOnGlobalLayoutListener
        )
    }

    /**
     * 댓글, 대댓글 recyclerView init + 아이템 클릭 이벤트
     */
    private fun initRecyclerView() {
        commentAdapter = CommentAdapter(requireContext(), true)

        binding.commentFragmentRvComment.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = commentAdapter
            adapter!!.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }

        boardViewModel.commentList.observe(viewLifecycleOwner) {
            commentAdapter.commentList = it
        }

        boardViewModel.commentListOnPost.observe(viewLifecycleOwner) {
            commentAdapter.commentAllList = it
        }

        commentAdapter.postUserId = boardViewModel.postDetail.value!!.user.id

        // 댓글, 대댓글 작성 클릭 이벤트
        commentAdapter.setAddReplyItemClickListener(object : CommentAdapter.ItemClickListener {
            override fun onClick(view: View, writerNick: String, position: Int, commentId: Int) {
                showKeyboard(binding.commentFragmentEtComment)

                binding.commentFragmentTvWriterNick.visibility = View.VISIBLE
                binding.commentFragmentTvWriterNick.text = writerNick

                parentId = commentId

                insertCommentAndReply()
            }
        })

        // 댓글 수정 클릭 이벤트
        commentAdapter.setModifyItemClickListener(object : CommentAdapter.MenuClickListener {
            override fun onClick(position: Int, commentId: Int, writerUserId: Int) {
                showKeyboard(binding.commentFragmentEtComment)
                
                val cmtList = boardViewModel.commentList.value!!
                binding.commentFragmentEtComment.setText(cmtList[position].content)

                updateComment(commentId, position, true)
            }
        })

        // 댓글 삭제 클릭 이벤트
        commentAdapter.setDeleteItemClickListener(object : CommentAdapter.MenuClickListener {
            override fun onClick(position: Int, commentId: Int, writerUserId: Int) {
                deleteComment(commentId, position, true)
            }
        })

        // 댓글 작성자에게 쪽지 보내기 클릭 이벤트
        commentAdapter.setSendNoteItemClickListener(object : CommentAdapter.MenuClickListener {
            override fun onClick(position: Int, commentId: Int, writerUserId: Int) {
                mainActivity.showDialogSendMessage(writerUserId, userId)

            }
        })

        // 댓글 신고 클릭 이벤트
        commentAdapter.setReportItemClickListener(object : CommentAdapter.MenuClickListener {
            override fun onClick(position: Int, commentId: Int, writerUserId: Int) {
                mainActivity.showReportDialog(commentId, false, null, commentAdapter, null, 0, false)
            }
        })

        // 대댓글 수정 클릭 이벤트
        commentAdapter.setReplyModifyItemClickListener(object : CommentAdapter.MenuClickListener {
            override fun onClick(position: Int, commentId: Int, writerUserId: Int) {
                showKeyboard(binding.commentFragmentEtComment)

                val cmtList = boardViewModel.commentListOnPost.value!!

                for (item in cmtList) {
                    if(item.id == commentId) {
                        binding.commentFragmentEtComment.setText(item.content)
                        break
                    }
                }

                updateComment(commentId, position, false)
            }
        })

        // 대댓글 삭제 클릭 이벤트
        commentAdapter.setReplyDeleteItemClickListener(object : CommentAdapter.MenuClickListener {
            override fun onClick(position: Int, commentId: Int, writerUserId: Int) {
                deleteComment(commentId, position, false)
            }
        })

        // 대댓글 작성자에게 쪽지 보내기 클릭 이벤트
        commentAdapter.setReplySendNoteItemClickListener(object : CommentAdapter.MenuClickListener {
            override fun onClick(position: Int, commentId: Int, writerUserId: Int) {
                mainActivity.showDialogSendMessage(writerUserId, userId)
            }
        })

        // 대댓글 신고 클릭 이벤트
        commentAdapter.setReplyReportItemClickListener(object : CommentAdapter.MenuClickListener {
            override fun onClick(position: Int, commentId: Int, writerUserId: Int) {
                mainActivity.showReportDialog(commentId, false, null, commentAdapter, null, 0, false)
            }
        })

    }

    /**
     * 댓글 및 대댓글 등록
     * parentId == -1 -> 댓글 등록
     * parentId > 0 -> 대댓글 등록
     */
    private fun insertCommentAndReply() {

        binding.commentFragmentTvConfirm.onThrottleClick {
            val commentContent = binding.commentFragmentEtComment.text.toString()

            if (parentId == -1 && commentContent.isNotEmpty()) {  // 댓글 등록

                val comment = Comment(
                    boardId = postId,
                    userId = userId,
                    content = commentContent
                )
                Log.d(TAG, "insertCommentAndReply: $comment")

                var response: Response<Any?>

                try {

                    runBlocking {
                        response = CommentService().addComment(comment)
                    }

                    if (response.isSuccessful) {

                        showCustomToast("댓글이 등록되었습니다.")
                        runBlocking {
                            boardViewModel.getCommentList(postId)
                        }
                        commentAdapter.notifyDataSetChanged()
                        clearEditText()
                        clearFocus(mainActivity)
                    } else {
                        showCustomToast("댓글 등록 실패")
                        Log.d(TAG, "insertCommentAndReply: ${response.message()}", )
                    }
                } catch (e: HttpException) {
                    Log.e(TAG, "insertCommentAndReply: ${e.response()}", )
                }

            } else if (parentId != -1 && contentLenChk(commentContent)) {   // 대댓글 작성
                val reply = Comment(
                    parent = parentId,
                    boardId = postId,
                    userId = userId,
                    content = commentContent,
                )
                Log.d(TAG, "insertCommentAndReply: $reply")
                var response: Response<Any?>

                try {

                    runBlocking {
                        response = CommentService().addComment(reply)
                    }

                    if (response.isSuccessful) {

                        showCustomToast("대댓글이 등록되었습니다.")
                        runBlocking {
                            boardViewModel.getCommentList(postId)
                        }
                        commentAdapter.notifyDataSetChanged()

                        clearEditText()
                        clearFocus(mainActivity)
                    } else {
                        showCustomToast("대댓글 등록 실패")
                        Log.d(TAG, "insertCommentAndReply: ${response.message()}",)
                    }
                } catch (e: HttpException) {
                    Log.e(TAG, "insertCommentAndReply: ${e.response()}", )
                }
            }
        }.addDisposable()
    }

    /**
     * content 길이 체크
     * @param input
     */
    private fun contentLenChk(input: String) : Boolean {
        return !(input.trim().isEmpty() || input.length > 255)
    }

    /**
     * 댓글 수정
     * @param commentId
     * @param position
     * @param adapterChk true - commentAdapter / false - replyAdapter
     */
    private fun updateComment(commentId: Int, position: Int, adapterChk: Boolean) {
        binding.commentFragmentTvConfirm.onThrottleClick {
            val commentContent = binding.commentFragmentEtComment.text.toString()

            if (commentId > 0 && contentLenChk(commentContent)) {
                val updateComment = Comment(commentId, commentContent)
                
                var response: Response<Any?>
                try {
                    runBlocking { 
                        response = CommentService().modifyComment(updateComment)
                    }
                    if(response.isSuccessful) {
                        showCustomToast("댓글이 수정되었습니다.")
                        runBlocking {
                            boardViewModel.getCommentList(postId)
                        }
                        if(adapterChk) {
                            commentAdapter.notifyItemChanged(position)
                        } else {
                            commentAdapter.commentReplyAdapter.notifyItemChanged(position)
                            commentAdapter.commentReplyAdapter.userNum = 0
                            commentAdapter.notifyDataSetChanged()
                        }
//                        commentAdapter.notifyDataSetChanged()
                        clearEditText()
                        clearFocus(mainActivity)
                    } else {
                        showCustomToast("댓글 수정 실패")
                        Log.d(TAG, "updateComment: ${response.code()}")
                    }
                } catch (e: HttpException) {
                    Log.e(TAG, "updateComment: ${e.response()}", )
                }
            }
        }.addDisposable()
    }


    /**
     * 댓글 삭제
     * @param commentId
     * @param position
     * @param adapterChk true - commentAdapter / false - replyAdapter
     */
    private fun deleteComment(commentId: Int, position: Int, adapterChk: Boolean) {
        var response: Response<Any?>
        try {
            runBlocking {
                response = CommentService().deleteComment(commentId)
            }
            if(response.isSuccessful) {
                showCustomToast("댓글이 삭제되었습니다.")
                runBlocking {
                    boardViewModel.getCommentList(postId)
                }
                if(adapterChk) {
                    commentAdapter.notifyItemRemoved(position)
                } else {
                    commentAdapter.commentReplyAdapter.notifyItemRemoved(position)
                    commentAdapter.commentReplyAdapter.userNum = 0
                    commentAdapter.notifyDataSetChanged()
                }

            } else {
                showCustomToast("댓글 삭제 실패")
                Log.d(TAG, "deleteComment: ${response.message()}", )
            }
        } catch (e: HttpException) {
            Log.e(TAG, "deleteComment: ${e.response()}", )
        }
    }




    /**
     * 댓글 주인 nick 초기화 및 comment text 초기화
     */
    private fun clearEditText() {
        binding.commentFragmentTvWriterNick.visibility = View.GONE
        binding.commentFragmentTvWriterNick.text = ""
        binding.commentFragmentEtComment.setText("")
    }










    /**
     * 키보드 UP/DOWN 감지 리스너
     */
    private var mOnGlobalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        val activityRootView: View =
            mainActivity.window.decorView.findViewById(android.R.id.content)
        val heightDiff = activityRootView.rootView.height - activityRootView.height
        if (lastHeightDiff == 0) {
            lastHeightDiff = heightDiff
        }
        if (heightDiff > lastHeightDiff) { //keyboard show
            isOpenKeyboard = true
        } else { //keyboard hide
            if (isOpenKeyboard) {
                clearFocus(mainActivity)
                binding.commentFragmentTvWriterNick.visibility = View.GONE
                binding.commentFragmentTvWriterNick.text = ""
                parentId = -1
                isOpenKeyboard = false
            }
        }
    }

    private fun clearFocus(activity: Activity) {
        val v: View = activity.currentFocus ?: return
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
        v.clearFocus()
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        clearEditText()
    }

    private fun showKeyboard(editText: EditText?) {
        if (editText == null) {
            return
        }
        editText.requestFocus()
        editText.setSelection(editText.length())
        mInputMethodManager.showSoftInput(
            editText,
            InputMethodManager.SHOW_FORCED or InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }

    /**
     * RxBinding의 Throttle 기능 사용하는 Button 함수
     * @param throttleSecond 해당 시간동안 중복 클릭 방지 (기본으로 1초)
     * @param subscribe 클릭 리스너 정의
     * @since 04/26/22
     * @author Jiwoo Choi
     */
    fun ImageView.onThrottleClick(throttleSecond: Long = 1, subscribe: (() -> Unit)? = null) = clicks()
        .throttleFirst(throttleSecond, TimeUnit.SECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
            subscribe?.invoke()
        }


    override fun onStop() {
        super.onStop()
        binding.commentFragment.viewTreeObserver.removeOnGlobalLayoutListener(mOnGlobalLayoutListener)
    }

    override fun onDestroyView() {
//        findViewById<ConstraintLayout>(R.id.commentFragment).viewTreeObserver.removeOnGlobalLayoutListener(mOnGlobalLayoutListener)
//        binding.commentFragment.viewTreeObserver.removeOnGlobalLayoutListener(mOnGlobalLayoutListener)
        mainActivity.hideBottomNavi(false)
        super.onDestroyView()
    }
}