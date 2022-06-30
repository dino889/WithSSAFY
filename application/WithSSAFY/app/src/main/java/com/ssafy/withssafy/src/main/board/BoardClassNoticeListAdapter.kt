package com.ssafy.withssafy.src.main.board

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.databinding.ItemBoardClassNoticeBinding
import com.ssafy.withssafy.databinding.ItemClassNoticeBinding
import com.ssafy.withssafy.src.dto.notice.Notice
import com.ssafy.withssafy.src.viewmodel.NoticeViewModel
import com.ssafy.withssafy.src.viewmodel.UserViewModel
import kotlinx.coroutines.runBlocking

class BoardClassNoticeListAdapter(val userViewModel: UserViewModel, val viewLifecycleOwner: LifecycleOwner, var isStudent : Boolean): RecyclerView.Adapter<BoardClassNoticeListAdapter.BoardClassNoticeListViewHolder>() {
    var list = mutableListOf<Notice>()
    inner class BoardClassNoticeListViewHolder(private val binding: ItemClassNoticeBinding ) : RecyclerView.ViewHolder(binding.root) {
        val moreBtn = binding.itemClassNoticeMore
        fun bind(notice : Notice) {
            binding.notice = notice
            runBlocking {
                userViewModel.getUserInfoAuth(notice.userId)
            }
            if(userViewModel.userInfoAuth.value != null)
                binding.user = userViewModel.userInfoAuth.value!!
            binding.executePendingBindings()

            if(isStudent) {
                moreBtn.visibility = View.GONE
            } else {
                moreBtn.visibility = View.VISIBLE
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardClassNoticeListAdapter.BoardClassNoticeListViewHolder {
        return BoardClassNoticeListViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_class_notice,parent,false))
    }

    override fun onBindViewHolder(holder: BoardClassNoticeListViewHolder, position: Int) {
        holder.apply {
            bind(list[position])

            moreBtn.setOnClickListener{
                moreCLickListener.onClick(it, position, list[position].id, list[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface MoreClickListener{
        fun onClick(view: View, position: Int, id: Int, notice: Notice)
    }

    private lateinit var moreCLickListener : BoardClassNoticeListAdapter.MoreClickListener
    fun setMoreClickListener(moreClickListener: BoardClassNoticeListAdapter.MoreClickListener) {
        this.moreCLickListener = moreClickListener
    }

}