package com.ssafy.withssafy.src.main.board

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentBoardListBinding
import com.ssafy.withssafy.src.main.MainActivity
import kotlinx.coroutines.runBlocking


class BoardListFragment : BaseFragment<FragmentBoardListBinding>(FragmentBoardListBinding::bind, R.layout.fragment_board_list) {
    private lateinit var mainActivity: MainActivity

    private lateinit var boardListAdapter: BoardListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        runBlocking {
            boardViewModel.getAllBoardType()
        }

        initListener()
        initAdapter()
    }

    private fun initListener() {

        // '내가 쓴 글' 클릭 이벤트
        binding.boardListFragmentClMyWrotePost.setOnClickListener {
            this@BoardListFragment.findNavController().navigate(R.id.action_boardListFragment_to_boardDetailFragment,
                bundleOf("typeId" to -1)
            )
        }

        // '댓글 단 글' 클릭 이벤트
        binding.boardListFragmentClPostInComment.setOnClickListener {
            this@BoardListFragment.findNavController().navigate(R.id.action_boardListFragment_to_boardDetailFragment,
                bundleOf("typeId" to -2)
            )
        }

        // '좋아요한 글' 클릭 이벤트
        binding.boardListFragmentClClippingPost.setOnClickListener {
            this@BoardListFragment.findNavController().navigate(R.id.action_boardListFragment_to_boardDetailFragment,
                bundleOf("typeId" to -3)
            )
        }

        // 'HOT 게시글' 클릭 이벤트
        binding.boardListFragmentClHotBoard.setOnClickListener {
            this@BoardListFragment.findNavController().navigate(R.id.action_boardListFragment_to_boardDetailFragment,
                bundleOf("typeId" to -4)
            )
        }
    }

    private fun initAdapter() {

        boardListAdapter = BoardListAdapter()

        boardViewModel.allBoardType.observe(viewLifecycleOwner) {
            boardListAdapter.list = it
        }

        binding.boardListFragmentRvBoardList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = boardListAdapter
            adapter!!.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }

        boardListAdapter.setItemClickListener(object : BoardListAdapter.ItemClickListener {

            override fun onClick(view: View, position: Int, boardId: Int) {
                this@BoardListFragment.findNavController().navigate(R.id.action_boardListFragment_to_boardDetailFragment,
                    bundleOf("typeId" to boardId)  // 게시판 타입
                )
            }
        })
    }
}