package com.ssafy.withssafy.src.main.board

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentBoardNoticeListBinding
import com.ssafy.withssafy.src.main.team.TeamAdapter
import kotlinx.coroutines.runBlocking

private const val TAG = "BoardNoticeListFragment"
class BoardNoticeListFragment : BaseFragment<FragmentBoardNoticeListBinding>(FragmentBoardNoticeListBinding::bind, R.layout.fragment_board_notice_list) {
    private lateinit var boardNoticeListAdapter: BoardNoticeListAdapter
    private var typeId = 0
    val userId = ApplicationClass.sharedPreferencesUtil.getUser().id
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            typeId = it.getInt("typeId")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = noticeViewModel
//        runBlocking {
//            userViewModel.getClassRoom(userId)
//            userViewModel.getClassRoomList()
//            noticeViewModel.getNoticeList(0)
//        }
        initAdapter()
        initTabLayout()
        initButton()
        Log.d(TAG, "onViewCreated: ")
    }

    private fun initButton() {
        binding.fragmentBoardNoticeListAppBarPrev.setOnClickListener {
            this@BoardNoticeListFragment.findNavController().popBackStack()
        }
    }

    private fun initAdapter() {
        boardNoticeListAdapter = BoardNoticeListAdapter()
        noticeViewModel.setNoticeFilterListDefault()
        boardNoticeListAdapter.list = noticeViewModel.noticeFilterList.value!!

        noticeViewModel.noticeFilterList.observe(viewLifecycleOwner) {
            boardNoticeListAdapter.list = it
        }
        binding.fragmentBoardNoticeListRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            adapter = boardNoticeListAdapter
        }
        boardNoticeListAdapter.setItemClickListener(object : BoardNoticeListAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int, id: Int) {
                var noticeId = bundleOf("noticeId" to id)
                this@BoardNoticeListFragment.findNavController().navigate(R.id.baordNoticeDetailFragment, noticeId)
            }

        })
    }

    private fun initTabLayout() {
        val types = arrayListOf("학습","평가","운영","사이트","기타")
        for(item in types){
            binding.frargmentBoardNoticeListTabLayout.addTab(binding.frargmentBoardNoticeListTabLayout.newTab().setText(item))
        }
        var tab = binding.frargmentBoardNoticeListTabLayout.getTabAt(typeId)
        tab?.select()
        noticeViewModel.getFilterNoticeList(typeId)
        binding.frargmentBoardNoticeListTabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab?.position == 0){
                    noticeViewModel.getFilterNoticeList(0)
                }else{
                    noticeViewModel.getFilterNoticeList(tab?.position!!)
                }
                boardNoticeListAdapter.notifyDataSetChanged()

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }



}