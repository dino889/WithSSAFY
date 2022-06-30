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
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentStudyBinding
import com.ssafy.withssafy.src.main.MainActivity
import com.ssafy.withssafy.src.network.service.StudyService
import kotlinx.coroutines.runBlocking
import retrofit2.Response

private const val TAG = "StudyFragment"
class StudyFragment : BaseFragment<FragmentStudyBinding>(FragmentStudyBinding::bind, R.layout.fragment_study) {
    private lateinit var teamAdapter: TeamAdapter
    private var lastFilterText = ""
    private lateinit var mainActivity : MainActivity
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
        binding.viewModel = teamViewModel
        runBlocking {
            teamViewModel.getStudys()
        }
        setListener()
    }

    private fun setListener(){
        initButtons()
        initTabLayout()
        initAdapter()
    }

    private fun initTabLayout(){
        val types = arrayListOf<String>("어학","프로그래밍","면접","취업","CS","자율","기타")
        for(item in types){
            binding.frargmentTeamTabLayout.addTab(binding.frargmentTeamTabLayout.newTab().setText(item))
        }
        teamAdapter = TeamAdapter(requireContext())

        binding.frargmentTeamTabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab?.position == 0){
                    teamAdapter.filter.filter("")
                }else{
                    teamAdapter.filter.filter(tab?.text.toString())
                    lastFilterText = tab?.text.toString()
                }

                teamAdapter.notifyDataSetChanged()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

    }
    private fun initButtons(){

    }
    private fun initAdapter(){
        teamAdapter = TeamAdapter(requireContext())

        teamAdapter.list = teamViewModel.studyList.value!!
        teamAdapter.filter.filter("")

        teamViewModel.studyList.observe(viewLifecycleOwner){
            teamAdapter.list = it
            teamAdapter.filteredList = teamAdapter.list
        }

        binding.fragmentTeamRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            adapter = teamAdapter
        }
        teamAdapter.setItemClickListener(object : TeamAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int, id: Int) {
                var studyId = bundleOf("studyId" to id)
                this@StudyFragment.findNavController().navigate(R.id.teamDetailFragment, studyId)
            }
        })
        teamAdapter.setDeleteClickListener(object : TeamAdapter.DeleteClickListener {
            override fun onClick(position: Int, id: Int) {
                deleteStudy(id, position)
            }
        })
        teamAdapter.setModifyClickListener(object : TeamAdapter.ModifyClickListener {
            override fun onClick(position: Int, id: Int) {
                var studyId = bundleOf("studyId" to id)
                this@StudyFragment.findNavController().navigate(R.id.teamWriteFragment, studyId)
            }
        })
    }
    private fun deleteStudy(id:Int, position:Int){
        var response : Response<Any?>
        runBlocking {
            response = StudyService().deleteStudy(id)
        }
        if(response.isSuccessful){
            showCustomToast("삭제되었습니다.")
            runBlocking {
                teamViewModel.getStudys()
            }
            teamAdapter.notifyItemRemoved(position)
            teamAdapter.filter.filter(lastFilterText)
        }else{
            Log.d(TAG, "deleteStudy: 노삭제")
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StudyFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onResume() {
        super.onResume()
        mainActivity.hideBottomNavi(false)
    }
}