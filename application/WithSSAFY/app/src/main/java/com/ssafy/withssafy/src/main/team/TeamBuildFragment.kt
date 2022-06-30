package com.ssafy.withssafy.src.main.team

import android.content.Context
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
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentTeamBuildBinding
import com.ssafy.withssafy.src.dto.study.Study
import com.ssafy.withssafy.src.main.MainActivity
import com.ssafy.withssafy.src.network.service.StudyService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Response

private const val TAG = "TeamBuildFragment"
class TeamBuildFragment : BaseFragment<FragmentTeamBuildBinding>(FragmentTeamBuildBinding::bind,R.layout.fragment_team_build) {
    private lateinit var mainActivity:MainActivity
    private lateinit var teamBuildAdapter: TeamBuildAdapter
    private var lastFilterText = ""
    private var classification = -1

    var commonList = arrayListOf<String>("웹 기술","웹 디자인","웹 IoT","모바일")
    var specializationList = arrayListOf<String>("인공지능영상","인공지능음성","빅데이터추천","빅데이터분산","P2P거래","디지털화폐","IoT제어")
    var freeList = arrayListOf<String>("자유주제","오픈소스","기업연계")

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
        runBlocking {
            userViewModel.getUser(ApplicationClass.sharedPreferencesUtil.getUser().id, 1)
            teamViewModel.getTeamBuildListByRoomId(userViewModel.loginUserInfo.value!!.classRoomId)
            teamViewModel.getTeamInfo()
        }
//        var teamDao = mainActivity.teamDB?.teamDao()
//
//        val job = CoroutineScope(Dispatchers.IO).launch {
//            team = teamDao?.getTeamTypes()
//        }
//        runBlocking {
//            job.join()
//        }

        setListener()
    }
    private fun setListener(){
        initSpinner()
        initAdapter()
    }
    private fun initAdapter(){
        teamBuildAdapter = TeamBuildAdapter(requireContext())
        var tmpList = mutableListOf<Study>()
        var team = teamViewModel.teamInfo.value!!

        teamViewModel.teamBuildList.observe(viewLifecycleOwner){
            tmpList = mutableListOf<Study>()
            if(team!=null){
                if(team!!.classification == 0){
                    for(item in it){
                        for(value in commonList){
                            if(item.category.equals(value)){
                                tmpList.add(item)
                            }
                        }
                    }
                }else if(team!!.classification == 1){
                    for(item in it){
                        for(value in specializationList){
                            if(item.category.equals(value)){
                                tmpList.add(item)
                            }
                        }
                    }
                }else if(team!!.classification == 2){
                    for(item in it){
                        for(value in freeList){
                            if(item.category.equals(value)){
                                tmpList.add(item)
                            }
                        }
                    }
                }
            }else{
                for(item in it){
                    for(value in commonList){
                        if(item.category.equals(value)){
                            tmpList.add(item)
                        }
                    }
                }
            }

            teamBuildAdapter.list = tmpList
            teamBuildAdapter.filteredList = tmpList
        }
        


        binding.fragmentTeamBuildRv.apply{
            layoutManager = LinearLayoutManager(requireContext())
            adapter = teamBuildAdapter

        }

        teamBuildAdapter.setItemClickListener(object: TeamBuildAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int, id: Int) {
                var studyId = bundleOf("studyId" to id)
                this@TeamBuildFragment.findNavController().navigate(R.id.teamDetailFragment,studyId)
            }
        })
        teamBuildAdapter.setModifyClickListener(object:TeamBuildAdapter.ModifyClickListener {
            override fun onClick(position: Int, id: Int) {
                var studyId = bundleOf("studyId" to id)
                this@TeamBuildFragment.findNavController().navigate(R.id.teamWriteFragment, studyId)
            }
        })
        teamBuildAdapter.setDeleteClickListener(object: TeamBuildAdapter.DeleteClickListener {
            override fun onClick(position: Int, id: Int) {
                Log.d(TAG, "onClick: $id $position")
                deleteTeamBuilding(id,position)
            }
        })
    }
    private fun initSpinner(){
        var team = teamViewModel.teamInfo.value!!
        if(team != null){
            if(team!!.classification == 0){
                for(item in commonList){
                    binding.fragmentTeamBuildTabLayout.addTab(binding.fragmentTeamBuildTabLayout.newTab().setText(item))
                }

            }else if(team!!.classification == 1){
                for(item in specializationList){
                    binding.fragmentTeamBuildTabLayout.addTab(binding.fragmentTeamBuildTabLayout.newTab().setText(item))
                }
            }else if(team!!.classification == 2){
                for(item in freeList){
                    binding.fragmentTeamBuildTabLayout.addTab(binding.fragmentTeamBuildTabLayout.newTab().setText(item))
                }
            }
        }else{
            for(item in commonList){
                binding.fragmentTeamBuildTabLayout.addTab(binding.fragmentTeamBuildTabLayout.newTab().setText(item))
            }
        }

        binding.fragmentTeamBuildTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab?.position == 0){
                    teamBuildAdapter.filter.filter("")
                }else{
                    teamBuildAdapter.filter.filter(tab?.text.toString())
                }
                lastFilterText = tab?.text.toString()
                teamBuildAdapter.notifyDataSetChanged()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }
    private fun deleteTeamBuilding(id:Int, position:Int){
        var response: Response<Any?>
        runBlocking {
            response = StudyService().deleteStudy(id)
        }
        if(response.isSuccessful){
            showCustomToast("삭제되었습니다.")
            runBlocking {
                teamViewModel.getTeamBuildListByRoomId(userViewModel.loginUserInfo.value!!.classRoomId)
            }
            teamBuildAdapter.notifyItemRemoved(position)
            Log.d(TAG, "deleteTeamBuilding: ${lastFilterText}")
            teamBuildAdapter.filter.filter(lastFilterText)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TeamBuildFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onResume() {
        super.onResume()
        mainActivity.hideBottomNavi(false)
    }
}