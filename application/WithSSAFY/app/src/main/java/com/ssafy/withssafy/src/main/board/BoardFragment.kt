package com.ssafy.withssafy.src.main.board

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentBoardBinding
import com.ssafy.withssafy.src.main.MainActivity


class BoardFragment : BaseFragment<FragmentBoardBinding>(FragmentBoardBinding::bind, R.layout.fragment_board) {
    private val TAG = "BoardFragment_ws"
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabAdapter()
    }

    private fun initTabAdapter() {
        val viewPagerAdapter = BoardPageAdapter(this)
        val tabList = listOf("게시판", "취업", "공지사항")

        viewPagerAdapter.addFragment(BoardListFragment())
        viewPagerAdapter.addFragment(BoardJobFragment())
        viewPagerAdapter.addFragment(BoardNoticeFragment())

        binding.boardFragmentVp.adapter = viewPagerAdapter
        TabLayoutMediator(binding.boardFragmentTabLayout, binding.boardFragmentVp) { tab, position ->
            tab.text = tabList[position]
        }.attach()
    }
}