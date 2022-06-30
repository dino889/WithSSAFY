package com.ssafy.withssafy.src.main.notification

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentMessageBinding
import kotlinx.coroutines.runBlocking

private const val TAG = "MessageFragment"
class MessageFragment : BaseFragment<FragmentMessageBinding>(FragmentMessageBinding::bind, R.layout.fragment_message) {
    private lateinit var groupAdapter: MessageGroupAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = messageViewModel
        runBlocking {
            messageViewModel.getMessageGroup(ApplicationClass.sharedPreferencesUtil.getUser().id)
        }
        setListener()
    }
    private fun setListener(){
        initAdapter()
    }
    private fun initAdapter(){
        groupAdapter = MessageGroupAdapter()
        messageViewModel.messageGroup.observe(viewLifecycleOwner){
            Log.d(TAG, "initAdapter: $it")
            groupAdapter.list = it
        }
        binding.fragmentMessageGroupRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = groupAdapter
        }
        groupAdapter.setItemClickListener(object : MessageGroupAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int, fromId: Int, toId:Int) {
                var groupId = bundleOf("fromId" to fromId,"toId" to toId)
                this@MessageFragment.findNavController().navigate(R.id.messageDetailFragment, groupId)
            }
        })
        val groupHelperCallback = MessageGroupHelperCallback().apply {
            setClamp(resources.displayMetrics.widthPixels.toFloat() / 4)
        }
        ItemTouchHelper(groupHelperCallback).attachToRecyclerView(binding.fragmentMessageGroupRv)
        binding.fragmentMessageGroupRv.setOnTouchListener { v, event ->
            groupHelperCallback.removePreviousClamp(binding.fragmentMessageGroupRv)
            false
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MessageFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}