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
import com.ssafy.withssafy.src.dto.notice.Notice
import com.ssafy.withssafy.src.viewmodel.NoticeViewModel
import com.ssafy.withssafy.src.viewmodel.UserViewModel
import kotlinx.coroutines.runBlocking

class BoardClassNoticeAdapter(val userViewModel: UserViewModel, val viewLifecycleOwner: LifecycleOwner): RecyclerView.Adapter<BoardClassNoticeAdapter.BoardClassNoticeViewHolder>() {
    var list = mutableListOf<Notice>()
    inner class BoardClassNoticeViewHolder(private val binding: ItemBoardClassNoticeBinding ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(notice : Notice) {
            binding.notice = notice

            runBlocking {
                userViewModel.getUserInfoAuth(notice.userId)
            }
            if(userViewModel.userInfoAuth.value != null)
                binding.user = userViewModel.userInfoAuth.value!!
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardClassNoticeAdapter.BoardClassNoticeViewHolder {
        return BoardClassNoticeViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_board_class_notice,parent,false))
    }

    override fun onBindViewHolder(holder: BoardClassNoticeViewHolder, position: Int) {
        holder.apply {
            bind(list[position])
            itemView.setOnClickListener {
                itemClickListener.onClick(it,position, list[position].id!!)
            }
        }
    }

    override fun getItemCount(): Int {
        if(list.size > 5)
            return 5
        else
            return list.size
    }

    private lateinit var itemClickListener : ItemClickListener
    interface ItemClickListener{
        fun onClick(view: View, position: Int, id: Int)
    }
    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }
}