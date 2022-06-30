package com.ssafy.withssafy.src.main.board

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.databinding.ItemNoticeListBinding
import com.ssafy.withssafy.src.dto.notice.Notice
import com.ssafy.withssafy.src.main.team.TeamAdapter

private const val TAG = "BoardNoticeListAdapter"
class BoardNoticeListAdapter : RecyclerView.Adapter<BoardNoticeListAdapter.BoardNoticeListViewHolder>(){
    var list = mutableListOf<Notice>()
    inner class BoardNoticeListViewHolder(private val binding: ItemNoticeListBinding) : RecyclerView.ViewHolder(binding.root) {
        var indexTv = binding.itemNoticeListIndex
        fun bind(notice : Notice, position: Int) {
            binding.notice = notice
            indexTv.text = (position + 1).toString()
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardNoticeListViewHolder {
        return BoardNoticeListViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_notice_list,parent,false))
    }

    override fun onBindViewHolder(holder: BoardNoticeListViewHolder, position: Int) {
        holder.apply {
            bind(list[position], position)
            itemView.setOnClickListener {
                itemClickListener.onClick(it,position, list[position].id!!)
            }
        }
    }

    override fun getItemCount(): Int {
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