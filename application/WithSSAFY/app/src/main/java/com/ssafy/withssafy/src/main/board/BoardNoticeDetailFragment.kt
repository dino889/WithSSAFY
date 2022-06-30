package com.ssafy.withssafy.src.main.board

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
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentBoardNoticeDetailBinding
import com.ssafy.withssafy.src.network.service.NoticeService
import com.ssafy.withssafy.src.network.service.RecruitService
import kotlinx.coroutines.runBlocking
import retrofit2.Response

private const val TAG = "BoardNoticeDetailFragment"
class BoardNoticeDetailFragment : BaseFragment<FragmentBoardNoticeDetailBinding>(FragmentBoardNoticeDetailBinding::bind, R.layout.fragment_board_notice_detail) {

    private var noticeId = 0
    val userId = ApplicationClass.sharedPreferencesUtil.getUser().id
    val studentId = ApplicationClass.sharedPreferencesUtil.getUser().studentId
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            noticeId = it.getInt("noticeId")
        }
        Log.d(TAG, "getNoticeOne: $noticeId")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = noticeViewModel
        runBlocking {
            noticeViewModel.getNotice(noticeId)
        }
        initData()
        initButton()
    }

    private fun initButton() {
        if(studentId != null) { // 교육생
            binding.fragmentBoardNoticeDetailMore.visibility = View.GONE
        } else { // 관리자
            binding.fragmentBoardNoticeDetailMore.visibility = View.VISIBLE
            binding.fragmentBoardNoticeDetailMore.setOnClickListener {
                showPopMenu(it, noticeId)
            }
        }
        binding.fragmentBoardNoticeDetailAppBarPrev.setOnClickListener{
            this@BoardNoticeDetailFragment.findNavController().popBackStack()
        }
    }

    private fun initData(){
        if(noticeViewModel.notice.value!!.photoPath == null || noticeViewModel.notice.value!!.photoPath == ""){
            binding.fragmentBoardNoticeDetailImg.visibility = View.GONE
        }
    }

    private fun showPopMenu(view: View, id: Int) {
        val popup = PopupMenu(context, view)
        MenuInflater(context).inflate(R.menu.recruit_popup_menu, popup.menu)
        popup.show()
        popup.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.update -> {
                    var noticeId = bundleOf("noticeId" to noticeId)
                    this@BoardNoticeDetailFragment.findNavController().navigate(R.id.noticeWriteFragment, noticeId)
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

    private fun updateData() {
        userViewModel.classRoomInfo.observe(viewLifecycleOwner) {
            var gen = it.generation
            userViewModel.classRommList.value!!.forEach { classRoom ->
                if(classRoom.generation == gen && classRoom.area == "전체" && classRoom.classDescription == "전체") {
                    noticeViewModel.setClassRoomId(classRoom.id)
                    noticeViewModel.classRoomId.observe(viewLifecycleOwner) {
                        runBlocking {
                            noticeViewModel.getNoticeList(it)
                        }
                    }
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
                userViewModel.getClassRoom(userId)
                userViewModel.getClassRoomList()
                noticeViewModel.getNoticeList(0)
            }
            updateData()
            this@BoardNoticeDetailFragment.findNavController().popBackStack()
        } else {
            Log.d(TAG, "공지사항 삭제 실패")
        }

    }

}