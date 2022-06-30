package com.ssafy.withssafy.src.main.schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentScheduleBinding
import com.ssafy.withssafy.src.main.notification.NotificationPageAdapter


class ScheduleFragment : BaseFragment<FragmentScheduleBinding>(FragmentScheduleBinding::bind,R.layout.fragment_schedule) {
    private lateinit var pagerAdapter:NotificationPageAdapter
    val studentId = ApplicationClass.sharedPreferencesUtil.getUser().studentId
    val userId = ApplicationClass.sharedPreferencesUtil.getUser().id
    private var isStudent = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
    }
    private fun setListener(){
        initAdmin()
        initToggle()
        initButtons()
    }
    private fun initButtons(){
        binding.fragmentScheduleAdminWrite.setOnClickListener {
            this@ScheduleFragment.findNavController().navigate(R.id.curriculumWriteFragment)
        }
    }
    private fun initAdmin(){
        if(studentId != null) { // 교육생
            binding.fragmentScheduleAdminWrite.visibility = View.GONE
            isStudent = true
        } else { // 관리자
            binding.fragmentScheduleAdminWrite.visibility = View.VISIBLE
            isStudent = false
        }
    }
    private fun initToggle(){
        pagerAdapter = NotificationPageAdapter(this)
        binding.fragmentScheduleViewPager.run {
            isUserInputEnabled = false
        }
        pagerAdapter.addFragment(FullCurriculumFragment())
        pagerAdapter.addFragment(ClassCurriculumFragment())

        binding.fragmentScheduleViewPager.adapter = pagerAdapter
        binding.fragmentScheduleViewPager.currentItem = 0
        binding.fragmentScheduleToggleFull.setOnClickListener {
            toggleClick(false)
            binding.fragmentScheduleViewPager.currentItem = 0
        }
        binding.fragmentScheduleToggleBan.setOnClickListener {
            toggleClick(true)
            binding.fragmentScheduleViewPager.currentItem = 1

        }
    }
    private fun toggleClick(clicked:Boolean){
        if(clicked){
            binding.fragmentScheduleToggleFull.setBackgroundResource(0)
            binding.fragmentScheduleToggleBan.setBackgroundResource(R.drawable.drawable_toggle_item_background)
        }else{
            binding.fragmentScheduleToggleFull.setBackgroundResource(R.drawable.drawable_toggle_item_background)
            binding.fragmentScheduleToggleBan.setBackgroundResource(0)
        }
    }
    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ScheduleFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}