package com.ssafy.withssafy.src.main.board

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentBoardNoticeBinding
import com.ssafy.withssafy.src.main.MainActivity
import kotlinx.coroutines.runBlocking

private const val TAG = "BoardNoticeFragment"
class BoardNoticeFragment : BaseFragment<FragmentBoardNoticeBinding>(FragmentBoardNoticeBinding::bind, R.layout.fragment_board_notice) {
    private lateinit var mainActivity: MainActivity
    lateinit var boardNoticeAllAdapter: BoardNoticeAllAdapter
    lateinit var boardClassNoticeAdapter: BoardClassNoticeAdapter

    val studentId = ApplicationClass.sharedPreferencesUtil.getUser().studentId
    val userId = ApplicationClass.sharedPreferencesUtil.getUser().id
    val classRoomId = ApplicationClass.sharedPreferencesUtil.getUser().classRoomId
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = noticeViewModel
        runBlocking {
            userViewModel.getClassRoomInfo(classRoomId)
            userViewModel.getClassRoomList()
        }
        initData()
        initSsafyRuleDetailToggle()
        initButton()
        initAdapter()
    }

    /**
     * 기수에 맞는 전체 ClassRoomId를 찾은 후 해당하는 ClassRoomId의 공지사항 목록 init
     */
    private fun initData() {
        userViewModel.classRoomInfo.observe(viewLifecycleOwner) {
            runBlocking {
                noticeViewModel.getClassNoticeList(it.id)
            }
            var gen = it.generation
            userViewModel.classRommList.value!!.forEach { classRoom ->
                if(classRoom.generation == gen && classRoom.area == "전체" && classRoom.classDescription == "전체") {
                    noticeViewModel.setClassRoomId(classRoom.id)
                    noticeViewModel.classRoomId.observe(viewLifecycleOwner) {
                        runBlocking {
                            noticeViewModel.getNoticeList(it)
                        }
                        boardNoticeAllAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    private fun initButton() {
        if(studentId != null) { // 교육생
            binding.boardNotiFragmentNoticeCFb.visibility = View.GONE
        } else { // 관리자
            binding.boardNotiFragmentNoticeCFb.visibility = View.VISIBLE
            binding.boardNotiFragmentNoticeCFb.setOnClickListener {
                var noticeId = bundleOf("noticeId" to 0)
                this@BoardNoticeFragment.findNavController().navigate(R.id.noticeWriteFragment, noticeId)
            }
        }
        binding.boardNotiFragmentClOperation.setOnClickListener { // 운영
            var typeId = bundleOf("typeId" to 3)
            this@BoardNoticeFragment.findNavController().navigate(R.id.boardNoticeListFragment, typeId)
        }
        binding.boardNotiFragmentClLearning.setOnClickListener { // 학습
            var typeId = bundleOf("typeId" to 1)
            this@BoardNoticeFragment.findNavController().navigate(R.id.boardNoticeListFragment, typeId)
        }
        binding.boardNotiFragmentClEvaluation.setOnClickListener { // 평가
            var typeId = bundleOf("typeId" to 2)
            this@BoardNoticeFragment.findNavController().navigate(R.id.boardNoticeListFragment, typeId)
        }
        binding.boardNotiFragmentClSite.setOnClickListener { // 사이트
            var typeId = bundleOf("typeId" to 4)
            this@BoardNoticeFragment.findNavController().navigate(R.id.boardNoticeListFragment, typeId)
        }
        binding.boardNotiFragmentClOther.setOnClickListener { // 기타
            var typeId = bundleOf("typeId" to 5)
            this@BoardNoticeFragment.findNavController().navigate(R.id.boardNoticeListFragment, typeId)
        }
        binding.boardNotiFragmentClassReadMore.setOnClickListener {
            this@BoardNoticeFragment.findNavController().navigate(R.id.boardClassNoticeListFragment)
        }
        binding.boardNotiFragmentLlMustReadMore.setOnClickListener {
            var typeId = bundleOf("typeId" to 0)
            this@BoardNoticeFragment.findNavController().navigate(R.id.boardNoticeListFragment, typeId)
        }
    }

    private fun initAdapter() {
        boardNoticeAllAdapter = BoardNoticeAllAdapter()
        noticeViewModel.noticeAllList.observe(viewLifecycleOwner) {
            boardNoticeAllAdapter.list = it
        }
        binding.boardNotiFragmentRvMustReadList.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = boardNoticeAllAdapter
        }
        boardNoticeAllAdapter.setItemClickListener(object : BoardNoticeAllAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int, id: Int) {
                var noticeId = bundleOf("noticeId" to id)
                this@BoardNoticeFragment.findNavController().navigate(R.id.baordNoticeDetailFragment, noticeId)
            }

        })

        boardClassNoticeAdapter = BoardClassNoticeAdapter(userViewModel, viewLifecycleOwner)
        noticeViewModel.classNoticeList.observe(viewLifecycleOwner) {
            Log.d(TAG, "initAdapter BoardClassNoticeAdapter: $it")
            boardClassNoticeAdapter.list = it
            boardClassNoticeAdapter.notifyDataSetChanged()
        }

        binding.boardNotiFragmentRvClassReadList.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = boardClassNoticeAdapter
        }
        boardClassNoticeAdapter.setItemClickListener(object : BoardClassNoticeAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int, id: Int) {
                var position = bundleOf("position" to position)
                this@BoardNoticeFragment.findNavController().navigate(R.id.boardClassNoticeListFragment, position)
                Log.d(TAG, "onClick: $position")
            }
        })
    }

    private fun initSsafyRuleDetailToggle() {
        val attendanceDetail = binding.boardNotiFragmentIvAttendanceDetail
        val attendance = binding.boardNotiFragmentIvAttendance

        attendanceDetail.setOnClickListener {
            if(attendance.visibility == View.GONE) { // content가 숨겨져 있는 경우
                attendanceDetail.setImageResource(R.drawable.ic_arrow_up)
                attendance.visibility = View.VISIBLE
            } else if(attendance.visibility == View.VISIBLE) {
                attendanceDetail.setImageResource(R.drawable.ic_arrow_down)
                attendance.visibility = View.GONE
            }
        }


        val lifeRuleDetail = binding.boardNotiFragmentIvLifeRuleDetail
        val lifeRule = binding.boardNotiFragmentIvLifeRule

        lifeRuleDetail.setOnClickListener {
            if(lifeRule.visibility == View.GONE) { // content가 숨겨져 있는 경우
                lifeRuleDetail.setImageResource(R.drawable.ic_arrow_up)
                lifeRule.visibility = View.VISIBLE
            } else if(lifeRule.visibility == View.VISIBLE) {
                lifeRuleDetail.setImageResource(R.drawable.ic_arrow_down)
                lifeRule.visibility = View.GONE
            }
        }


        val mileageDetail = binding.boardNotiFragmentIvMileageDetail
        val mileage = binding.boardNotiFragmentIvMileage

        mileageDetail.setOnClickListener {
            if(mileage.visibility == View.GONE) { // content가 숨겨져 있는 경우
                mileageDetail.setImageResource(R.drawable.ic_arrow_up)
                mileage.visibility = View.VISIBLE
            } else if(mileage.visibility == View.VISIBLE) {
                mileageDetail.setImageResource(R.drawable.ic_arrow_down)
                mileage.visibility = View.GONE
            }
        }

    }

}