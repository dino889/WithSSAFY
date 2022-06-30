package com.ssafy.withssafy.src.main.board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.animation.content.Content
import com.ssafy.withssafy.R
import com.ssafy.withssafy.databinding.ItemBoardBinding
import com.ssafy.withssafy.src.dto.board.BoardType

class BoardListAdapter : RecyclerView.Adapter<BoardListAdapter.ViewHolder>() {
    var list = mutableListOf<BoardType>()

    inner class ViewHolder(private val binding: ItemBoardBinding) : RecyclerView.ViewHolder(binding.root) {
        val boardItem = binding.boardListItemClBoard

        fun bind(boardType: BoardType) {
            binding.boardType = boardType
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_board, parent, false))
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val item = list[position]
        holder.apply {
            bind(item)
            boardItem.setOnClickListener {
                itemClickListener.onClick(it, position, item.id)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface ItemClickListener{
        fun onClick(view: View, position: Int, boardId: Int)
    }

    private lateinit var itemClickListener : ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }
}