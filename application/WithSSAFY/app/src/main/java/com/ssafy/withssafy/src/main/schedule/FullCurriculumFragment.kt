package com.ssafy.withssafy.src.main.schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentFullCurriculumBinding
import kotlinx.coroutines.runBlocking

class FullCurriculumFragment : BaseFragment<FragmentFullCurriculumBinding>(FragmentFullCurriculumBinding::bind,R.layout.fragment_full_curriculum) {
    private lateinit var calendarMonthAdapter:CalenderMonthAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        runBlocking {
            userViewModel.getUser(ApplicationClass.sharedPreferencesUtil.getUser().id, 1)
            scheduleViewModel.getAllGenarateSchedules(userViewModel.loginUserInfo.value!!.classRoomId)
        }
        setListener()
    }
    private fun setListener(){
        initAdapter()
    }
    private fun initAdapter(){
        var date = arrayListOf<String>()
        scheduleViewModel.allGenarateSchedules.observe(viewLifecycleOwner){
            for(item in it){
                date.add(item.startDate)
            }
        }
        calendarMonthAdapter = CalenderMonthAdapter(requireContext(),date,scheduleViewModel,userViewModel.loginUserInfo.value!!.classRoomId,viewLifecycleOwner,this)

        binding.fragmentScheduleFullRv.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter = calendarMonthAdapter
            scrollToPosition(Int.MAX_VALUE/2)
        }

        val snap = PagerSnapHelper()
        if(binding.fragmentScheduleFullRv.onFlingListener == null){
            snap.attachToRecyclerView(binding.fragmentScheduleFullRv)
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FullCurriculumFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}