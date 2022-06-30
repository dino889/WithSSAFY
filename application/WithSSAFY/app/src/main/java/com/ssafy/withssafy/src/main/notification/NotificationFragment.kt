package com.ssafy.withssafy.src.main.notification

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentNotificationBinding
import com.ssafy.withssafy.src.main.MainActivity
import com.ssafy.withssafy.src.main.board.BoardJobFragment
import com.ssafy.withssafy.src.main.board.BoardListFragment
import com.ssafy.withssafy.src.main.board.BoardNoticeFragment
import com.ssafy.withssafy.src.main.board.BoardPageAdapter


class NotificationFragment : BaseFragment<FragmentNotificationBinding>(FragmentNotificationBinding::bind, R.layout.fragment_notification) {
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
        val viewPagerAdapter = NotificationPageAdapter(this)
        val tabList = listOf("알림", "쪽지")

        viewPagerAdapter.addFragment(NotiListFragment())
        viewPagerAdapter.addFragment(MessageFragment())

        binding.notificationFragmentVp.adapter = viewPagerAdapter
        TabLayoutMediator(binding.notificationFragmentTabLayout, binding.notificationFragmentVp) { tab, position ->
            tab.text = tabList[position]
        }.attach()

        // ViewPager 스와이프 막기
        binding.notificationFragmentVp.isUserInputEnabled = false
    }


}