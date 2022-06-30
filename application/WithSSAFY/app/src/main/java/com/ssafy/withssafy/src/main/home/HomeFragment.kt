package com.ssafy.withssafy.src.main.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.databinding.FragmentHomeBinding
import com.ssafy.withssafy.src.dto.board.Board
import com.ssafy.withssafy.src.main.MainActivity
import com.ssafy.withssafy.src.main.board.BoardClassNoticeAdapter
import com.ssafy.withssafy.src.main.board.JobAdapter
import com.ssafy.withssafy.src.viewmodel.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private const val TAG = "HomeFragment"
// : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home)
class HomeFragment : Fragment(){

    private lateinit var binding : FragmentHomeBinding
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val recruitViewModel : RecruitViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private val noticeViewModel: NoticeViewModel by activityViewModels()
    private val boardViewModel: BoardViewModel by activityViewModels()
    private lateinit var mainActivity: MainActivity

    //lateinit var favoriteBoardAdapter: FavoriteBoardAdapter
    lateinit var boardClassNoticeAdapter: BoardClassNoticeAdapter
    lateinit var popularPostAdapter: PopularPostAdapter
    lateinit var employInfoAdapter: EmployInfoAdapter
    lateinit var requestAdapter: RequestAdapter
    lateinit var reportAdapter: ReportAdapter

    val userId = ApplicationClass.sharedPreferencesUtil.getUser().id
    val studentId = ApplicationClass.sharedPreferencesUtil.getUser().studentId
    val classRoomId = ApplicationClass.sharedPreferencesUtil.getUser().classRoomId

    // 롤링 배너
    private lateinit var bannerViewPagerAdapter: BannerViewPagerAdapter
    private var isRunning = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recruitViewModel = recruitViewModel
        binding.userViewModel = userViewModel
        binding.noticeViewModel = noticeViewModel
        binding.homeViewModel = homeViewModel

        runBlocking {
            recruitViewModel.getRecentRecruitList()
            userViewModel.getStateZeroUserList()
            noticeViewModel.getClassNoticeList(classRoomId)
            homeViewModel.getReportList()
            boardViewModel.getHotPostList()
            boardViewModel.getUserLikePostList(userId)
        }

        val bannerList = arrayListOf<Int>(R.drawable.banner1, R.drawable.banner2, R.drawable.banner3)
        homeViewModel.setBannerItems(bannerList)

        setListener()
    }
    private fun setListener(){
        initAdmin()
        initViewPager()
        initButtons()
        subscribeObservers()
        autoScrollViewPage()
        initAdapter()
    }
    private fun initAdmin(){
        Log.d(TAG, "onViewCreated: $studentId")
        if(studentId != null) { // 교육생
            binding.homeLayoutAdminRequestLayout.visibility = View.GONE
            binding.homeFragmentClReport.visibility = View.GONE
        } else { // 관리자
            binding.homeLayoutAdminRequestLayout.visibility = View.VISIBLE
            binding.homeFragmentClReport.visibility = View.VISIBLE
        }
    }
    private fun initButtons(){
        binding.fragmentHomeLinkEduSsafy.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://edu.ssafy.com/comm/login/SecurityLoginForm.do"))
            startActivity(intent)
        }
        binding.fragmentHomeLinkUser.setOnClickListener {
            this@HomeFragment.findNavController().navigate(R.id.userFragment)
        }
        binding.fragmentHomeLinkGitSsafy.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://project.ssafy.com/home"))
            startActivity(intent)
        }
        binding.fragmentHomeLinkJobSsafy.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://job.ssafy.com/job/main/index.do"))
            startActivity(intent)
        }
        binding.fragmentHomeLinkYoutubeSsafy.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UC_XI3ByFO1uZIIH-g-zJZiw"))
            startActivity(intent)
        }
        binding.fragmentHomeMoreNotice.setOnClickListener {
            mainActivity.hideBottomNavi(true)
            this@HomeFragment.findNavController().navigate(R.id.boardClassNoticeListFragment)
        }
        binding.fragmentHomeMoreJob.setOnClickListener {
            var typeId = bundleOf("typeId" to 1)
            this@HomeFragment.findNavController().navigate(R.id.boardFragment, typeId)
        }
        binding.fragmentHomeMoreRequest.setOnClickListener {
            this@HomeFragment.findNavController().navigate(R.id.requestFragment)
        }
        binding.homeIvPopularMore.setOnClickListener {
            this@HomeFragment.findNavController().navigate(R.id.action_homeFragment_to_boardDetailFragment, bundleOf("typeId" to -4))
        }
        binding.homeFragmentClMoreReport.setOnClickListener {
            this@HomeFragment.findNavController().navigate(R.id.action_homeFragment_to_reportFragment)
        }
    }
    private fun initAdapter() {
        boardClassNoticeAdapter = BoardClassNoticeAdapter(userViewModel, viewLifecycleOwner)
        noticeViewModel.classNoticeList.observe(viewLifecycleOwner) {
            Log.d(TAG, "initAdapter BoardClassNoticeAdapter: $it")
            boardClassNoticeAdapter.list = it
        }

        binding.homeRvClassNotice.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = boardClassNoticeAdapter
        }
        boardClassNoticeAdapter.setItemClickListener(object : BoardClassNoticeAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int, id: Int) {
                var position = bundleOf("position" to position)
                this@HomeFragment.findNavController().navigate(R.id.boardClassNoticeListFragment, position)
                Log.d(TAG, "onClick: $position")
            }
        })

        popularPostAdapter = PopularPostAdapter()

        boardViewModel.hotPostList.observe(viewLifecycleOwner) {
            popularPostAdapter.list = it
        }
        boardViewModel.userLikePostList.observe(viewLifecycleOwner) {
            popularPostAdapter.userLikePost = it
        }

        binding.homeRvPopular.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = popularPostAdapter
        }

        popularPostAdapter.setItemClickListener(object : PopularPostAdapter.ItemClickListener {
            override fun onClick(view: View, board: Board) {
                this@HomeFragment.findNavController().navigate(R.id.action_homeFragment_to_postDetailFragment,
                    bundleOf("postId" to board.id, "typeId" to board.boardType.id))
            }
        })

        employInfoAdapter = EmployInfoAdapter()
        recruitViewModel.recentRecruitList.observe(viewLifecycleOwner) {
            employInfoAdapter.list = it
        }
        binding.homeRvEmploy.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = employInfoAdapter
        }
        employInfoAdapter.setItemClickListener(object : EmployInfoAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int, id: Int) {
                mainActivity.hideBottomNavi(true)
                var recruitId = bundleOf("recruitId" to id)
                this@HomeFragment.findNavController().navigate(R.id.jobDetailFragment, recruitId)
            }
        })

        requestAdapter = RequestAdapter(true)
        userViewModel.stateZeroUserList.observe(viewLifecycleOwner) {
            requestAdapter.list = it
        }
        binding.homeRvRequest.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = requestAdapter
        }
        requestAdapter.setItemClickListener(object : RequestAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int, id: Int) {

            }
        })

        reportAdapter = ReportAdapter(true)
        homeViewModel.reportList.observe(viewLifecycleOwner) {
            reportAdapter.list = it
        }

        binding.homeFragmentRvReport.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = reportAdapter
        }
        reportAdapter.setItemClickListener(object : ReportAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int, id: Int) {

            }
        })
    }

    /**
     * home 화면 롤링 배너 Recycler View Adapter
     */
    private fun initViewPager() {
        binding.viewPager.apply {
            bannerViewPagerAdapter = BannerViewPagerAdapter(requireContext())
            adapter = bannerViewPagerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    isRunning = true
                }
            })
        }
    }

    private fun subscribeObservers() {
        homeViewModel.bannerItemList.observe(mainActivity, Observer{ bannerItemList ->
            bannerViewPagerAdapter.submitList(bannerItemList)

        })
        homeViewModel.currentPosition.observe(mainActivity, Observer { currentPosition ->
            binding.viewPager.currentItem = currentPosition
        })
    }

    private fun autoScrollViewPage() {
        lifecycleScope.launch{
            whenResumed {
                while(isRunning) {
                    delay(3000)
                    homeViewModel.getcurrentPosition()?.let {
                        homeViewModel.setCurrentPosition((it.plus(1)) % 3)
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}