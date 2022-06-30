package com.ssafy.withssafy.src.main.team

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentTeamBinding
import com.ssafy.withssafy.src.main.MainActivity
import com.ssafy.withssafy.src.main.notification.NotificationPageAdapter
import com.ssafy.withssafy.src.network.service.StudyService
import com.ssafy.withssafy.src.viewmodel.TeamViewModel
import kotlinx.coroutines.runBlocking
import retrofit2.Response

private const val TAG = "TeamFragment"
class TeamFragment : BaseFragment<FragmentTeamBinding>(FragmentTeamBinding::bind,R.layout.fragment_team) {
    private lateinit var pagerAdapter: NotificationPageAdapter

    private var userId = ApplicationClass.sharedPreferencesUtil.getUser().id
    private var studentId = ApplicationClass.sharedPreferencesUtil.getUser().studentId

    private lateinit var mainActivity:MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
    }
    private fun setListener(){
        initViewPager()
        initButtons()
    }
    private fun initViewPager(){
        pagerAdapter = NotificationPageAdapter(this)
        val tabList = listOf("스터디","팀빌딩")
        binding.teamFragmentVp.run{
            isUserInputEnabled = false
        }
        pagerAdapter.addFragment(StudyFragment())
        pagerAdapter.addFragment(TeamBuildFragment())

        binding.teamFragmentVp.adapter = pagerAdapter

        TabLayoutMediator(binding.teamFragmentTabLayout, binding.teamFragmentVp){ tab, position ->
            tab.text = tabList[position]
        }.attach()
    }
    private fun initButtons(){
        binding.fragmentTeamWrite.setOnClickListener {
            if(studentId != null){
                this@TeamFragment.findNavController().navigate(R.id.teamWriteFragment)
            }else{
                this@TeamFragment.findNavController().navigate(R.id.teamAdminFragment)
            }
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TeamFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onResume() {
        super.onResume()
        mainActivity.hideBottomNavi(false)
    }
}