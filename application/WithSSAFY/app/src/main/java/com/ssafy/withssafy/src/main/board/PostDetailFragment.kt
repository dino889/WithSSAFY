package com.ssafy.withssafy.src.main.board

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.jakewharton.rxbinding3.view.clicks
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentPostDetailBinding
import com.ssafy.withssafy.src.dto.board.LikeDto
import com.ssafy.withssafy.src.main.MainActivity
import com.ssafy.withssafy.src.network.service.BoardService
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import retrofit2.Response
import java.util.concurrent.TimeUnit

/**
 * @since 04/26/22
 * @author Jiwoo Choi
 */
class PostDetailFragment : BaseFragment<FragmentPostDetailBinding>(FragmentPostDetailBinding::bind, R.layout.fragment_post_detail) {
    private val TAG = "PostDetailFragment_ws"
    private lateinit var mainActivity: MainActivity

    private val userId = ApplicationClass.sharedPreferencesUtil.getUser().id
    private var postWriteId : Int = -1

    private var postId = -1
    private var typeId = -1


    private lateinit var commentAdapter: CommentAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            postId = getInt("postId")
            typeId = getInt("typeId")
        }
    }

    override fun onResume() {
        super.onResume()
        mainActivity.hideBottomNavi(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.hideBottomNavi(true)

        runBlocking {
            boardViewModel.getPostDetail(postId)
            boardViewModel.getLikePostOrNot(postId, userId)
            boardViewModel.getCommentList(postId)
        }


        initDataBinding()
        initListener()
        initCommentRecyclerView()

    }

    private fun initDataBinding() {
        boardViewModel.postDetail.observe(viewLifecycleOwner) {
            binding.post = it
            postWriteId = it.user.id
        }

        boardViewModel.likePost.observe(viewLifecycleOwner) {
            val heart = binding.postDetailFragmentLottieHeart

            if(it) {
                heart.progress = 0.5F
            } else {
                heart.progress = 0.0F
            }
        }
    }

    private fun initListener() {
        backBtnClickEvent()
        commentLayoutClickEvent()
        heartClickEvent()
        moreBtnClickEvent()
    }
    /**
     * ???????????? ?????? ?????? ?????????
     */
    private fun backBtnClickEvent() {
        binding.postDetailFragmentIbBack.setOnClickListener {
            this@PostDetailFragment.findNavController().popBackStack()
        }
    }

    /**
     * ?????? ?????? ??? ?????? ?????????
     */
    private fun commentLayoutClickEvent() {

        binding.postDetailFragmentClInsertCmt.setOnClickListener {
            this@PostDetailFragment.findNavController().navigate(R.id.action_postDetailFragment_to_commentFragment,
                bundleOf("postId" to postId))
        }
    }

    /**
     * ????????? ????????? ?????? ?????????
     */
    private fun heartClickEvent() {
        val heart = binding.postDetailFragmentLottieHeart
        heart.onThrottleClick {
            val like = LikeDto(id = 0, boardId = postId, userId = userId)
            var response: Response<Any?>
            runBlocking {
                response = BoardService().likePost(like)
            }
            if(response.isSuccessful) {
                runBlocking {
                    boardViewModel.getPostDetail(postId)
//                    boardViewModel.getLikePostOrNot(postId, userId)
                }
                if(heart.progress > 0.3f) {
                    val animator = ValueAnimator.ofFloat(1f,0f).setDuration(500)
                    animator.addUpdateListener { animation ->
                        heart.progress = animation.animatedValue as Float
                    }
                    animator.start()
                } else {
                    val animator = ValueAnimator.ofFloat(0f,0.4f).setDuration(500)
                    animator.addUpdateListener { animation ->
                        heart.progress = animation.animatedValue as Float
                    }
                    animator.start()
                }
            } else {
                Log.e(TAG, "heartClickEvent: ?????? ?????? $response")
            }
        }
    }

    /**
     * ????????? more ?????? ?????? ?????????
     */
    private fun moreBtnClickEvent() {
        binding.postDetailFragmentBtnMore.setOnClickListener {
            val popup = PopupMenu(context, binding.postDetailFragmentBtnMore)
            // ???????????? ????????? ???????????? ?????? popup_menu_write ?????? ??????
            if(postWriteId == userId) {
                MenuInflater(context).inflate(R.menu.popup_menu_writer, popup.menu)

                popup.show()
                popup.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.modify -> {
                            this@PostDetailFragment.findNavController().navigate(R.id.action_postDetailFragment_to_postWriteFragment,
                                bundleOf("typeId" to typeId, "postId" to postId)
                            )
                            return@setOnMenuItemClickListener true
                        }
                        R.id.delete -> {
                            // ????????? ??????
                            deletePost(postId)
                            return@setOnMenuItemClickListener true
                        }
                        else -> {
                            return@setOnMenuItemClickListener false
                        }
                    }
                }
            } else {    // ???????????? ?????? ?????? popup_menu(?????? ?????????, ??????)
                MenuInflater(context).inflate(R.menu.popup_menu, popup.menu)

                popup.show()
                popup.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.sendNote -> {  // ?????? ????????? -> ?????? ????????? id ??????
                            mainActivity.showDialogSendMessage(postWriteId,ApplicationClass.sharedPreferencesUtil.getUser().id)
                            return@setOnMenuItemClickListener true
                        }
                        R.id.report -> {    // ?????? -> ?????? ????????? id, ?????? id
                            mainActivity.showReportDialog(postId, true, null, null, null, 0, false)
                            return@setOnMenuItemClickListener true
                        }
                        else -> {
                            return@setOnMenuItemClickListener false
                        }
                    }
                }
            }
        }
    }

    /**
     * ????????? ??????
     */
    private fun deletePost(postId: Int) {
        try {
            var response : Response<Any?>
            runBlocking {
                response = BoardService().deletePost(postId)
            }
            if(response.isSuccessful) {
                showCustomToast("???????????? ?????????????????????.")
                this@PostDetailFragment.findNavController().popBackStack()
            } else {
                showCustomToast("????????? ?????? ??????")
                Log.d(TAG, "deletePost: ${response.message()}", )
            }
        } catch (e: HttpException) {
            Log.e(TAG, "deletePost: ${e.response()}", )
        }
    }

    /**
     * ?????? recyclerView ?????????
     */
    private fun initCommentRecyclerView() {

        commentAdapter = CommentAdapter(requireContext(), true)

        binding.postDetailFragmentRvComment.apply {
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

        rvItemClickEvent()
    }

    /**
     * ??????, ????????? recyclerView click event init
     */
    private fun rvItemClickEvent() {
        // ??????, ????????? ?????? ?????? ?????????
        commentAdapter.setAddReplyItemClickListener(object : CommentAdapter.ItemClickListener {
            override fun onClick(view: View, writerNick: String, position: Int, commentId: Int) {
                this@PostDetailFragment.findNavController().navigate(R.id.action_postDetailFragment_to_commentFragment,
                    bundleOf("postId" to postId))
            }
        })

        // ?????? ?????? ?????? ?????????
        commentAdapter.setModifyItemClickListener(object : CommentAdapter.MenuClickListener {
            override fun onClick(position: Int, commentId: Int, writerUserId: Int) {
                this@PostDetailFragment.findNavController().navigate(R.id.action_postDetailFragment_to_commentFragment,
                    bundleOf("postId" to postId))
            }
        })

        // ?????? ?????? ?????? ?????????
        commentAdapter.setDeleteItemClickListener(object : CommentAdapter.MenuClickListener {
            override fun onClick(position: Int, commentId: Int, writerUserId: Int) {
                this@PostDetailFragment.findNavController().navigate(R.id.action_postDetailFragment_to_commentFragment,
                    bundleOf("postId" to postId))
            }
        })

        // ?????? ??????????????? ?????? ????????? ?????? ?????????
        commentAdapter.setSendNoteItemClickListener(object : CommentAdapter.MenuClickListener {
            override fun onClick(position: Int, commentId: Int, writerUserId: Int) {
                mainActivity.showDialogSendMessage(writerUserId, userId)
            }
        })

        // ?????? ?????? ?????? ?????????
        commentAdapter.setReportItemClickListener(object : CommentAdapter.MenuClickListener {
            override fun onClick(position: Int, commentId: Int, writerUserId: Int) {
                mainActivity.showReportDialog(commentId, false, null, commentAdapter, null, 0, false)
            }
        })

        // ????????? ?????? ?????? ?????????
        commentAdapter.setReplyModifyItemClickListener(object : CommentAdapter.MenuClickListener {
            override fun onClick(position: Int, commentId: Int, writerUserId: Int) {
                this@PostDetailFragment.findNavController().navigate(R.id.action_postDetailFragment_to_commentFragment,
                    bundleOf("postId" to postId))
            }
        })

        // ????????? ?????? ?????? ?????????
        commentAdapter.setReplyDeleteItemClickListener(object : CommentAdapter.MenuClickListener {
            override fun onClick(position: Int, commentId: Int, writerUserId: Int) {
                this@PostDetailFragment.findNavController().navigate(R.id.action_postDetailFragment_to_commentFragment,
                    bundleOf("postId" to postId))
            }
        })

        // ????????? ??????????????? ?????? ????????? ?????? ?????????
        commentAdapter.setReplySendNoteItemClickListener(object : CommentAdapter.MenuClickListener {
            override fun onClick(position: Int, commentId: Int, writerUserId: Int) {
                mainActivity.showDialogSendMessage(writerUserId, userId)
            }
        })

        // ????????? ?????? ?????? ?????????
        commentAdapter.setReplyReportItemClickListener(object : CommentAdapter.MenuClickListener {
            override fun onClick(position: Int, commentId: Int, writerUserId: Int) {
                mainActivity.showReportDialog(commentId, false, null, commentAdapter, null, 0, false)
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        mainActivity.hideBottomNavi(false)
    }

    /**
     * RxBinding??? Throttle ?????? ???????????? Button ??????
     * @param throttleSecond ?????? ???????????? ?????? ?????? ?????? (???????????? 1???)
     * @param subscribe ?????? ????????? ??????
     */
    fun LottieAnimationView.onThrottleClick(throttleSecond: Long = 1, subscribe: (() -> Unit)? = null) = clicks()
        .throttleFirst(throttleSecond, TimeUnit.SECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
            subscribe?.invoke()
        }
}