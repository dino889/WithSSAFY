package com.ssafy.withssafy.src.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.databinding.RecyclerviewPopularItemBinding
import com.ssafy.withssafy.src.dto.board.Board

class PopularPostAdapter() : RecyclerView.Adapter<PopularPostAdapter.PopularPostHolder>() {

    lateinit var list: MutableList<Board>
    lateinit var userLikePost: MutableList<Board>

    val userId = ApplicationClass.sharedPreferencesUtil.getUser().id

    inner class PopularPostHolder(val binding: RecyclerviewPopularItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val heartBtn = binding.rvItemPopularLottieHeart
        val boardItem = binding.rvItemCl

        fun bind(board: Board) {
            binding.board = board

            for (item in userLikePost) {   // 로그인 유저가 좋아요 누른 게시글 표시
                if(board.id == item.id) {
                    heartBtn.progress = 0.5F
                    break
                }
                heartBtn.progress = 0.0F
            }

            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularPostHolder {
        return PopularPostHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.recyclerview_popular_item, parent, false))
    }

    override fun onBindViewHolder(holder: PopularPostHolder, position: Int) {
        val item = list[position]
        holder.apply {
            bind(item)
            boardItem.setOnClickListener {
                itemClickListener.onClick(it, item)
            }
        }
    }

    override fun getItemCount(): Int {
        return if(list.size > 5) {
            5
        } else {
            list.size
        }
    }

    interface ItemClickListener {
        fun onClick(view: View, board: Board)
    }

    private lateinit var itemClickListener : ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }
}