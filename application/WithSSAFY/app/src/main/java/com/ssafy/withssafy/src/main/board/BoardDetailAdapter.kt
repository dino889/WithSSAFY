package com.ssafy.withssafy.src.main.board

import android.content.Context
import android.view.*
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.databinding.ItemPostListBinding
import com.ssafy.withssafy.src.dto.User
import com.ssafy.withssafy.src.dto.board.Board
import java.util.*

class BoardDetailAdapter (val context: Context, val typeId : Int) : RecyclerView.Adapter<BoardDetailAdapter.ViewHolder>(){
//class LocalBoardAdapter(val context: Context) : ListAdapter<Board, LocalBoardAdapter.LocalBoardViewHolder>(DiffCallback) {
    lateinit var postList : MutableList<Board>
    lateinit var userLikePost: MutableList<Board>

    val userId = ApplicationClass.sharedPreferencesUtil.getUser().id

    inner class ViewHolder(private val binding: ItemPostListBinding) : RecyclerView.ViewHolder(binding.root) {

        val heartBtn = binding.postListItemLottieHeart
        val commentBtn = binding.postListItemLlComment
        val moreBtn = binding.postListItemBtnMore
        val item = binding.postListItemCl

        fun bindInfo(board: Board) {
            if(typeId > 0) {
                binding.postListItemTvBoardType.visibility = View.GONE
                binding.postListItemViewLine1.visibility = View.GONE
            }
            for (item in userLikePost) {   // 로그인 유저가 좋아요 누른 게시글 표시
                if(board.id == item.id) {
                    heartBtn.progress = 0.5F
                    break
                }
                heartBtn.progress = 0.0F
            }

            binding.board = board
            binding.executePendingBindings()
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_post_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val post = getItem(position)
        val post = postList[position]
        holder.apply {
            bindInfo(post)
            item.setOnClickListener {
                itemClickListener.onClick(it, position, post.id, post.boardType.id)
            }
//            setIsRecyclable(false)
//            heartBtn.setOnClickListener {
//                heartItemClickListener.onClick(it as LottieAnimationView, position, post.id)
//            }
//
//            commentBtn.setOnClickListener {
//                commentItemClickListener.onClick(it, post.id)
//            }
//
            moreBtn.setOnClickListener {
                val popup = PopupMenu(context, moreBtn)
                // 작성자인 경우 popup_menu_write 팝업 메뉴
                if(post.user.id == userId) {
                    MenuInflater(context).inflate(R.menu.popup_menu_writer, popup.menu)

                    popup.show()
                    popup.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.modify -> {
                                modifyItemClickListener.onClick(position, post)
                                return@setOnMenuItemClickListener true
                            }
                            R.id.delete -> {
                                deleteItemClickListener.onClick(position, post)
                                return@setOnMenuItemClickListener true
                            }
                            else -> {
                                return@setOnMenuItemClickListener false
                            }
                        }
                    }
                } else {    // 작성자가 아닌 경우 popup_menu(쪽지 보내기, 신고)
                    MenuInflater(context).inflate(R.menu.popup_menu, popup.menu)

                    popup.show()
                    popup.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.sendNote -> {  // 쪽지 보내기 -> 댓글 작성자 id 필요
                                sendNoteItemClickListener.onClick(position, post)
                                return@setOnMenuItemClickListener true
                            }
                            R.id.report -> {    // 신고 -> 댓글 작성자 id, 댓글 id
                                reportItemClickListener.onClick(position, post)
                                return@setOnMenuItemClickListener true
                            }
                            else -> {
                                return@setOnMenuItemClickListener false
                            }
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    interface HeartItemClickListener {
        fun onClick(heart: LottieAnimationView, position: Int, id: Int)
    }


    private lateinit var heartItemClickListener : HeartItemClickListener

    fun setHeartItemClickListener(itemClickListener: HeartItemClickListener) {
        this.heartItemClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int, postId: Int, typeId: Int)
    }

    private lateinit var itemClickListener : ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    interface MenuClickListener {
        fun onClick(position: Int, board: Board)
    }

    private lateinit var modifyItemClickListener : MenuClickListener
    fun setModifyItemClickListener(modifyClickListener: MenuClickListener) {
        this.modifyItemClickListener = modifyClickListener
    }

    private lateinit var deleteItemClickListener : MenuClickListener
    fun setDeleteItemClickListener(deleteClickListener: MenuClickListener) {
        this.deleteItemClickListener = deleteClickListener
    }

    private lateinit var sendNoteItemClickListener : MenuClickListener
    fun setSendNoteItemClickListener(sendNoteClickListener: MenuClickListener) {
        this.sendNoteItemClickListener = sendNoteClickListener
    }

    private lateinit var reportItemClickListener : MenuClickListener
    fun setReportItemClickListener(reportClickListener: MenuClickListener) {
        this.reportItemClickListener = reportClickListener
    }

//    object DiffCallback : DiffUtil.ItemCallback<Board>() {
//        override fun areItemsTheSame(oldItem: Board, newItem: Board): Boolean {
//            return oldItem === newItem
//        }
//
//        override fun areContentsTheSame(oldItem: Board, newItem: Board): Boolean {
//            return oldItem.id == newItem.id
//        }
//    }



}