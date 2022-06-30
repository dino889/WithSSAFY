package com.ssafy.withssafy.src.main.notification

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentNotiListBinding
import com.ssafy.withssafy.src.main.MainActivity
import com.ssafy.withssafy.src.main.home.FavoriteBoardAdapter
import kotlinx.coroutines.runBlocking

private const val TAG = "NotiListFragment"
class NotiListFragment : BaseFragment<FragmentNotiListBinding>(FragmentNotiListBinding::bind, R.layout.fragment_noti_list) {
    private lateinit var mainActivity: MainActivity
    val userId = ApplicationClass.sharedPreferencesUtil.getUser().id
    lateinit var notiListAdapter: NotiListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = notificationViewModel
        runBlocking {
            notificationViewModel.getNotiList(userId)
        }
        initSpinner()
        initAdapter()
    }

    private fun initSpinner() {
        val txtArr = arrayListOf("전체 알림", "공지사항 알림", "사용자 알림", "취업공고 알림")
        val spin = binding.notiListSp
        spin.apply {
            adapter = ArrayAdapter.createFromResource(requireContext(), R.array.noti, android.R.layout.simple_spinner_dropdown_item)
        }
        spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                binding.notiListTv1.setText(txtArr.get(position))
                when (position) {
                    0 -> {
                        runBlocking {
                            notificationViewModel.getNotiList(userId)
                        }
                        notiListAdapter.notifyDataSetChanged()
                    }
                    else -> {
                        runBlocking {
                            notificationViewModel.getNotiListByType(position, userId)
                        }
                        notiListAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun initAdapter() {
        notiListAdapter = NotiListAdapter(requireContext())
        notificationViewModel.notiList.observe(viewLifecycleOwner) {
            notiListAdapter.list = it
        }
        binding.notiListRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = notiListAdapter
        }
        // 구분선 추가
        binding.notiListRv.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        // Recyclerview 스와이프해서 DELETE
        val notiListRvHelperCallback = NotiListRvHelperCallback().apply {
            setClamp(resources.displayMetrics.widthPixels.toFloat() / 4)
        }

        ItemTouchHelper(notiListRvHelperCallback).attachToRecyclerView(binding.notiListRv)

        binding.notiListRv.setOnTouchListener{ _, _ ->
            notiListRvHelperCallback.removePreviousClamp(binding.notiListRv)
            false
        }

    }

}