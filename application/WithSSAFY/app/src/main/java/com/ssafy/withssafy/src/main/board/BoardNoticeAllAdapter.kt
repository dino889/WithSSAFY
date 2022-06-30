package com.ssafy.withssafy.src.main.board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.databinding.ItemBoardNotiMustreadBinding
import com.ssafy.withssafy.src.dto.notice.Notice

class BoardNoticeAllAdapter : RecyclerView.Adapter<BoardNoticeAllAdapter.BoardNoticeAllViewHolder>(){
    var list = mutableListOf<Notice>()
    inner class BoardNoticeAllViewHolder(private val binding: ItemBoardNotiMustreadBinding) : RecyclerView.ViewHolder(binding.root) {
        var categoryTv = binding.boardNotiMustReadItemCategory
        fun bind(notice : Notice) {
            binding.notice = notice
            binding.executePendingBindings()

            if(notice.typeId == 1) categoryTv.text = "학습"
            else if(notice.typeId == 2) categoryTv.text = "평가"
            else if(notice.typeId == 3) categoryTv.text = "운영"
            else if(notice.typeId == 4) categoryTv.text = "사이트"
            else if(notice.typeId == 5) categoryTv.text = "기타"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardNoticeAllViewHolder {
        return BoardNoticeAllViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_board_noti_mustread,parent,false))
    }

    override fun onBindViewHolder(holder: BoardNoticeAllViewHolder, position: Int) {
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