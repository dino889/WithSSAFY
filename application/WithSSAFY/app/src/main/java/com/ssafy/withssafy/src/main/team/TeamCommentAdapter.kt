package com.ssafy.withssafy.src.main.team

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.databinding.ItemCommentListBinding
import com.ssafy.withssafy.src.dto.board.Comment
import com.ssafy.withssafy.src.main.board.ReplyAdapter

class TeamCommentAdapter(val context: Context, val boardOrStudy: Boolean) : RecyclerView.Adapter<TeamCommentAdapter.CommentViewHolder>(){
    lateinit var commentList: MutableList<Comment>
    lateinit var commentAllList : MutableList<Comment>
    var commentReplyAdapter = ReplyAdapter(context, boardOrStudy)
    var postUserId: Int = -1
    val userId = ApplicationClass.sharedPreferencesUtil.getUser().id

    inner class CommentViewHolder(private val binding:ItemCommentListBinding) : RecyclerView.ViewHolder(binding.root){
        val nick = binding.commentItemTvUserNick
        val moreBtn = binding.commentItemIvMoreBtn
        val addReply = binding.commentItemIbAddReply

        fun bind(data: Comment){
            binding.comment = data
            if(data.userId == postUserId){
                nick.setTextColor(Color.parseColor("#2C64BF"))
                nick.text = "익명(글쓴이)"
            }
            binding.executePendingBindings()

            // 대댓글 rv adapter 추가하기
            val replyList = mutableListOf<Comment>()
            for (reply in commentAllList) {
                if(reply.parentId == data.id) {
                    replyList.add(reply)
                }
            }
//            commentReplyAdapter = ReplyAdapter(context)
//                commentNestedAdapter.submitList(list)
            commentReplyAdapter.commentList = replyList
            commentReplyAdapter.postUserId = postUserId

            binding.commentItemRvReply.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
                adapter = commentReplyAdapter
                adapter!!.stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
            }
            commentReplyAdapter.setDeleteItemClickListener(object : ReplyAdapter.MenuClickListener {

                override fun onClick(commentId: Int, postId: Int, position: Int) {
//                    deleteReply(commentId, postId, position)
                }
            })
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TeamCommentAdapter.CommentViewHolder {
        return CommentViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_comment_list,parent,false))
    }

    override fun onBindViewHolder(holder: TeamCommentAdapter.CommentViewHolder, position: Int) {
        val comment = commentList[position]
        holder.apply {
            bind(commentList[position])
            addReply.setOnClickListener {
                addReplyClickListener.onClick(it, nick.text.toString(), position, comment.id)
            }

            moreBtn.setOnClickListener {
                val popup = PopupMenu(context, moreBtn)
                // 작성자인 경우 popup_menu_write 팝업 메뉴
                if(comment.userId == userId) {
                    MenuInflater(context).inflate(R.menu.popup_menu_writer, popup.menu)

                    popup.show()
                    popup.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.modify -> {
                                menuItemClickListener.onClick(position, comment.id, comment.userId)
                                return@setOnMenuItemClickListener true
                            }
                            R.id.delete -> {
                                menuItemClickListener.onClick(position, comment.id, comment.userId)
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
                                menuItemClickListener.onClick(position, comment.id, comment.userId)
                                return@setOnMenuItemClickListener true
                            }
                            R.id.report -> {    // 신고 -> 댓글 작성자 id, 댓글 id
                                menuItemClickListener.onClick(position, comment.id, comment.userId)
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
        return commentList.size
    }

    interface ItemClickListener {
        fun onClick(view: View, writerNick: String, position: Int, commentId: Int)
    }

    private lateinit var addReplyClickListener : ItemClickListener
    fun setAddReplyItemClickListener(itemClickListener: ItemClickListener) {
        this.addReplyClickListener = itemClickListener
    }


    interface MenuClickListener {
        fun onClick(position: Int, commentId: Int, userId: Int)
    }

    private lateinit var menuItemClickListener : MenuClickListener
    fun setMenuItemClickListener(menuClickListener: MenuClickListener) {
        this.menuItemClickListener = menuClickListener
    }
}