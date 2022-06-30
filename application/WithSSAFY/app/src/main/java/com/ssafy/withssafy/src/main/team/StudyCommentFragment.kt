package com.ssafy.withssafy.src.main.team

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.jakewharton.rxbinding3.view.clicks
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentStudyCommentBinding
import com.ssafy.withssafy.src.dto.board.Comment
import com.ssafy.withssafy.src.main.MainActivity
import com.ssafy.withssafy.src.main.board.CommentAdapter
import com.ssafy.withssafy.src.network.service.CommentService
import com.ssafy.withssafy.src.network.service.StudyService
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import retrofit2.Response
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

private const val TAG = "StudyCommentFragment"
class StudyCommentFragment : BaseFragment<FragmentStudyCommentBinding>(FragmentStudyCommentBinding::bind,R.layout.fragment_study_comment) {
    lateinit var mainActivity:MainActivity
    private lateinit var mInputMethodManager: InputMethodManager
    private lateinit var studyCommentAdapter : CommentAdapter

    private var studyId by Delegates.notNull<Int>()
    private var parentId = -1
    val userId = ApplicationClass.sharedPreferencesUtil.getUser().id
    var lastHeightDiff = 0
    var isOpenKeyboard = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            studyId = it.getInt("studyId")
        }
        mInputMethodManager = mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        mOnGlobalLayoutListener.onGlobalLayout()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.hideBottomNavi(true)
        runBlocking {
            teamViewModel.getStudyCommentByBoardId(studyId)
        }
        setListener()
    }

    private fun setListener(){
        initButtons()
        initAdapter()
        layoutListener()
        insertCommentAndReply()
    }

    private fun initButtons(){
        binding.studyCommentFragmentIbBack.setOnClickListener {
            this@StudyCommentFragment.findNavController().popBackStack()
        }
    }

    private fun initAdapter(){
        studyCommentAdapter = CommentAdapter(requireContext(), false)
        binding.studyCommentFragmentRvComment.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = studyCommentAdapter
            adapter!!.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }

        teamViewModel.studyParentComments.observe(viewLifecycleOwner){
            Log.d(TAG, "initAdapter: $it")
            studyCommentAdapter.commentList = it
        }

        teamViewModel.studyComments.observe(viewLifecycleOwner){
            Log.d(TAG, "initAdapter: $it")

            studyCommentAdapter.commentAllList = it
        }

        studyCommentAdapter.postUserId = teamViewModel.study.value!!.user!!.id

        // 댓글, 대댓글 작성 클릭 이벤트
        studyCommentAdapter.setAddReplyItemClickListener(object : CommentAdapter.ItemClickListener {
            override fun onClick(view: View, writerNick: String, position: Int, commentId: Int) {
                showKeyboard(binding.studyCommentFragmentEtComment)
                binding.studyCommentFragmentTvWriterNick.visibility = View.VISIBLE
                binding.studyCommentFragmentTvWriterNick.text = writerNick

                parentId = commentId
                Log.d(TAG, "onClick: $parentId")

                insertCommentAndReply()
            }
        })

        // 댓글 수정 클릭 이벤트
        studyCommentAdapter.setModifyItemClickListener(object : CommentAdapter.MenuClickListener {
            override fun onClick(position: Int, commentId: Int, writerUserId: Int) {
                showKeyboard(binding.studyCommentFragmentEtComment)

                val cmtList = teamViewModel.studyComments.value!!
                binding.studyCommentFragmentEtComment.setText(cmtList[position].content)

                updateComment(commentId, position, true)
            }
        })

        // 댓글 삭제 클릭 이벤트
        studyCommentAdapter.setDeleteItemClickListener(object : CommentAdapter.MenuClickListener {
            override fun onClick(position: Int, commentId: Int, writerUserId: Int) {
                deleteComment(commentId, position, true)
            }
        })

        // 댓글 작성자에게 쪽지 보내기 클릭 이벤트
        studyCommentAdapter.setSendNoteItemClickListener(object : CommentAdapter.MenuClickListener {
            override fun onClick(position: Int, commentId: Int, writerUserId: Int) {
                mainActivity.showDialogSendMessage(writerUserId, userId)
            }
        })

        // 댓글 신고 클릭 이벤트
        studyCommentAdapter.setReportItemClickListener(object : CommentAdapter.MenuClickListener {
            override fun onClick(position: Int, commentId: Int, writerUserId: Int) {
                mainActivity.showReportDialog(commentId, false, null, studyCommentAdapter, null, 0, false)

            }
        })

        // 대댓글 수정 클릭 이벤트
        studyCommentAdapter.setReplyModifyItemClickListener(object : CommentAdapter.MenuClickListener {
            override fun onClick(position: Int, commentId: Int, writerUserId: Int) {
                showKeyboard(binding.studyCommentFragmentEtComment)

                val cmtList = teamViewModel.studyComments.value!!

                for (item in cmtList) {
                    if(item.id == commentId) {
                        binding.studyCommentFragmentEtComment.setText(item.content)
                        break
                    }
                }

                updateComment(commentId, position, false)
            }
        })

        // 대댓글 삭제 클릭 이벤트
        studyCommentAdapter.setReplyDeleteItemClickListener(object : CommentAdapter.MenuClickListener {
            override fun onClick(position: Int, commentId: Int, writerUserId: Int) {
                deleteComment(commentId, position, false)
            }
        })

        // 대댓글 작성자에게 쪽지 보내기 클릭 이벤트
        studyCommentAdapter.setReplySendNoteItemClickListener(object : CommentAdapter.MenuClickListener {
            override fun onClick(position: Int, commentId: Int, writerUserId: Int) {
                mainActivity.showDialogSendMessage(writerUserId, userId)
            }
        })

        // 대댓글 신고 클릭 이벤트
        studyCommentAdapter.setReplyReportItemClickListener(object : CommentAdapter.MenuClickListener {
            override fun onClick(position: Int, commentId: Int, writerUserId: Int) {
                mainActivity.showReportDialog(commentId, false, null, studyCommentAdapter, null, 0, false)
            }
        })
    }

    private fun insertCommentAndReply(){
        binding.studyCommentFragmentTvConfirm.onThrottleClick{
            val commentContent = binding.studyCommentFragmentEtComment.text.toString()
            var response : Response<Any?>

            if(parentId == -1 && commentContent.isNotEmpty()){
                val comment = Comment(
                    studyId,
                    userId,
                    commentContent
                )
                try{
                    runBlocking {
                        response = StudyService().insertStudyComment(comment)
                    }
                    if(response.isSuccessful){
                        showCustomToast("댓글 등록 성공")
                        runBlocking {
                            teamViewModel.getStudyCommentByBoardId(studyId)
                        }
                        studyCommentAdapter.notifyDataSetChanged()
                        clearEditText()
                        clearFocus(mainActivity)
                    }else{
                        Log.d(TAG, "insertCommentAndReply: ")
                    }

                }catch (e:HttpException){
                    Log.e(TAG, "insertCommentAndReply: ${e.message()}", )
                }
            }else if(parentId != -1 && contentLenChk(commentContent)){
                val reply = Comment(
                    parentId,
                    studyId,
                    userId,
                    commentContent
                )
                try{
                    runBlocking {
                        response = StudyService().insertStudyComment(reply)
                    }
                    if(response.isSuccessful){
                        showCustomToast("대댓글 등록성공")
                        runBlocking {
                            teamViewModel.getStudyCommentByBoardId(studyId)
                        }
                        studyCommentAdapter.notifyDataSetChanged()
                        clearEditText()
                        clearFocus(mainActivity)
                    } else {
                        showCustomToast("대댓글 등록 실패")
                        Log.d(TAG, "insertCommentAndReply: ${response.message()}",)
                    }
                }catch (e:HttpException){
                    Log.e(TAG, "insertCommentAndReply: ${e.message()}", )
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
        binding.studyCommentFragmentTvConfirm.onThrottleClick {
            val commentContent = binding.studyCommentFragmentEtComment.text.toString()

            if (commentId > 0 && contentLenChk(commentContent)) {
                val updateComment = Comment(commentId, commentContent)

                var response: Response<Any?>
                try {
                    runBlocking {
                        response = StudyService().modifyStudyComment(updateComment)
                    }
                    if(response.isSuccessful) {
                        showCustomToast("댓글이 수정되었습니다.")
                        runBlocking {
                            teamViewModel.getStudyCommentByBoardId(studyId)
                        }
                        if(adapterChk) {
                            studyCommentAdapter.notifyItemChanged(position)
                        } else {
                            studyCommentAdapter.commentReplyAdapter.notifyItemChanged(position)
                            studyCommentAdapter.commentReplyAdapter.userNum = 0
                            studyCommentAdapter.notifyDataSetChanged()
                        }
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
                response = StudyService().deleteStudyComment(commentId)
            }
            if(response.isSuccessful) {
                showCustomToast("댓글이 삭제되었습니다.")
                runBlocking {
                    teamViewModel.getStudyCommentByBoardId(studyId)
                }
                if(adapterChk) {
                    studyCommentAdapter.notifyItemRemoved(position)
                } else {
                    studyCommentAdapter.commentReplyAdapter.notifyItemRemoved(position)
                    studyCommentAdapter.commentReplyAdapter.userNum = 0
                    studyCommentAdapter.notifyDataSetChanged()
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
     * 댓글 조회 레이아웃에 키보드 감지 리스너 등록
     */
    private fun layoutListener() {
        binding.studyCommentFragment.viewTreeObserver.addOnGlobalLayoutListener(
            mOnGlobalLayoutListener
        )
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
                binding.studyCommentFragmentTvWriterNick.visibility = View.GONE
                binding.studyCommentFragmentTvWriterNick.text = ""
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

    /**
     * 댓글 주인 nick 초기화 및 comment text 초기화
     */
    private fun clearEditText() {
        binding.studyCommentFragmentTvWriterNick.visibility = View.GONE
        binding.studyCommentFragmentTvWriterNick.text = ""
        binding.studyCommentFragmentEtComment.setText("")
    }


    override fun onStop() {
        super.onStop()
        binding.studyCommentFragment.viewTreeObserver.removeOnGlobalLayoutListener(mOnGlobalLayoutListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivity.hideBottomNavi(false)
    }
}