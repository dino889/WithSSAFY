package com.ssafy.withssafy.src.main.board

import android.content.Context
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.databinding.FragmentBoardJobBinding
import com.ssafy.withssafy.src.dto.RecruitLike
import com.ssafy.withssafy.src.main.MainActivity
import com.ssafy.withssafy.src.main.team.TeamFragment
import com.ssafy.withssafy.src.network.service.RecruitService
import kotlinx.coroutines.runBlocking
import retrofit2.Response

private const val TAG = "BoardJobFragment"
class BoardJobFragment : BaseFragment<FragmentBoardJobBinding>(FragmentBoardJobBinding::bind,R.layout.fragment_board_job) {
    private lateinit var mainActivity: MainActivity
    private lateinit var jobAdapter: JobAdapter

    val studentId = ApplicationClass.sharedPreferencesUtil.getUser().studentId
    val userId = ApplicationClass.sharedPreferencesUtil.getUser().id
    private var isStudent = false
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
        binding.viewModel = recruitViewModel
        runBlocking {
            recruitViewModel.getRecruitList()
            recruitViewModel.getLikeRecruitList(userId)
        }
        setListener()
    }
    private fun setListener(){
        initAdmin()
        initButtons()
        initAdapter()
    }
    private fun initAdmin(){
        if(studentId != null) { // 교육생
            binding.fragmentJobAdminWrite.visibility = View.GONE
            isStudent = true
        } else { // 관리자
            binding.fragmentJobAdminWrite.visibility = View.VISIBLE
            isStudent = false
        }
    }
    private fun initButtons(){
        binding.fragmentJobAdminWrite.setOnClickListener {
            var recruitId = bundleOf("recruitId" to 0)
            this@BoardJobFragment.findNavController().navigate(R.id.action_boardJobFragment_to_adminJobWriteFragment, recruitId)
        }
    }
    private fun initAdapter(){
        jobAdapter = JobAdapter(isStudent)
        recruitViewModel.recruitList.observe(viewLifecycleOwner) {
            jobAdapter.list = it
        }
        binding.fragmentJobRv.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            adapter = jobAdapter
        }
        jobAdapter.setItemClickListener(object : JobAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int, id: Int) {
                var recruitId = bundleOf("recruitId" to id)
                this@BoardJobFragment.findNavController().navigate(R.id.jobDetailFragment, recruitId)
            }

        })
        jobAdapter.setHeartClickListener(object : JobAdapter.HeartClickListener {
            override fun onClick(view: View, position: Int, id: Int) {
                jobLikeAndCancel(id)
            }

        })
        jobAdapter.setMoreClickListener(object : JobAdapter.MoreClickListener {
            override fun onClick(view: View, position: Int, id: Int) {
                showPopMenu(view, id)
            }
        })

        recruitViewModel.likeRecruitList.observe(viewLifecycleOwner) {
            var likeRecruitList = arrayListOf<Int>()
            it.forEach {
                likeRecruitList.add(it.id)
            }
            jobAdapter.likeRecruitIdList = likeRecruitList
        }

    }

    private fun showPopMenu(view: View, id: Int) {
        val popup = PopupMenu(context, view)
        MenuInflater(context).inflate(R.menu.recruit_popup_menu, popup.menu)
        popup.show()
        popup.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.update -> {
                    var recruitId = bundleOf("recruitId" to id)
                    this@BoardJobFragment.findNavController().navigate(R.id.action_boardJobFragment_to_adminJobWriteFragment, recruitId)
                    return@setOnMenuItemClickListener true
                }
                R.id.delete -> {
                    deleteRecruit(id)
                    return@setOnMenuItemClickListener true
                } else -> {
                return@setOnMenuItemClickListener false
                    }
            }
        }
    }

    private fun deleteRecruit(id: Int) {
        var response: Response<Any?>
        runBlocking {
            response = RecruitService().deleteRecruitById(id)
        }
            Log.d(TAG, "likeAndCancelRecruit: ${response.body()}")
            if(response.code() == 204) {
                showCustomToast("삭제되었습니다.")
                runBlocking {
                    recruitViewModel.getRecruitList()
                }
                jobAdapter.notifyDataSetChanged()

            } else {
                Log.d(TAG, "채용공고 삭제 실패")
            }

    }

    private fun jobLikeAndCancel(id: Int) {
        var recruitLike = RecruitLike(id, userId)
        runBlocking {
            val response = RecruitService().likeAndCancelRecruit(recruitLike)
            Log.d(TAG, "likeAndCancelRecruit: ${response.body()}")
           
            if(response.code() == 204) {
                Log.d(TAG, "jobLikeAndCancel: 채용 공고 찜하기 & 찜하기 취소 성공")
      
            } else {
                Log.d(TAG, "jobLikeAndCancel: 채용 공고 찜하기 & 찜하기 취소 실패")
            }
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BoardJobFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}