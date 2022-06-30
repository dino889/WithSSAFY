package com.ssafy.withssafy.src.main.board

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentBoardClassNoticeListBinding
import com.ssafy.withssafy.src.dto.notice.Notice
import com.ssafy.withssafy.src.main.MainActivity
import com.ssafy.withssafy.src.network.service.NoticeService
import kotlinx.coroutines.runBlocking
import retrofit2.Response

private const val TAG = "BoardClassNoticeListFragment"
class BoardClassNoticeListFragment : BaseFragment<FragmentBoardClassNoticeListBinding>(FragmentBoardClassNoticeListBinding::bind, R.layout.fragment_board_class_notice_list) {
    private lateinit var mainActivity: MainActivity
    private lateinit var boardClassNoticeListAdapter: BoardClassNoticeListAdapter
    private var position = 0
    private var isStudent = false
    val classRoomId = ApplicationClass.sharedPreferencesUtil.getUser().classRoomId
    val studentId = ApplicationClass.sharedPreferencesUtil.getUser().studentId

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt("position")
        }
    }

    override fun onPause() {
        super.onPause()
        mainActivity.hideBottomNavi(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = noticeViewModel
        runBlocking {
            noticeViewModel.getClassNoticeList(classRoomId)
        }
        initAdmin()
        initAdapter()
        initButton()
    }
    private fun initAdmin(){
        isStudent = studentId != null
    }
    private fun initButton() {
        binding.fragmentBoardClassNoticeListAppBarPrev.setOnClickListener {
            this@BoardClassNoticeListFragment.findNavController().popBackStack()
        }
    }

    private fun initAdapter() {
        boardClassNoticeListAdapter = BoardClassNoticeListAdapter(userViewModel, viewLifecycleOwner, isStudent)
        noticeViewModel.classNoticeList.observe(viewLifecycleOwner) {
            it.reverse()
            boardClassNoticeListAdapter.list = it
            binding.fragmentBoardClassNoticeListRv.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
                adapter = boardClassNoticeListAdapter
            }
            Log.d(TAG, "initAdapter position: $position")
            if(position == 0) {
                binding.fragmentBoardClassNoticeListRv.scrollToPosition(it.size - 1)
            } else {
                binding.fragmentBoardClassNoticeListRv.smoothScrollToPosition(position)
            }
        }
        boardClassNoticeListAdapter.setMoreClickListener(object : BoardClassNoticeListAdapter.MoreClickListener {
            override fun onClick(view: View, position: Int, id: Int, notice: Notice) {
                showPopMenu(view, id, notice)
            }

        })
    }

    private fun showPopMenu(view: View, id: Int, notice: Notice) {
        val popup = PopupMenu(context, view)
        MenuInflater(context).inflate(R.menu.recruit_popup_menu, popup.menu)
        popup.show()
        popup.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.update -> {
                    noticeViewModel.setNotice(notice)
                    var noticeId = bundleOf("noticeId" to id)
                    this@BoardClassNoticeListFragment.findNavController().navigate(R.id.noticeWriteFragment, noticeId)
                    return@setOnMenuItemClickListener true
                }
                R.id.delete -> {
                    deleteNotice(id)
                    return@setOnMenuItemClickListener true
                } else -> {
                return@setOnMenuItemClickListener false
            }
            }
        }
    }

    private fun deleteNotice(id: Int) {
        var response: Response<Any?>
        runBlocking {
            response = NoticeService().deleteNoticeById(id)
        }
        if(response.code() == 204) {
            showCustomToast("삭제되었습니다.")
            runBlocking {
                noticeViewModel.getClassNoticeList(classRoomId)
            }
        } else {
            Log.d(TAG, "공지사항 삭제 실패")
        }

    }





}