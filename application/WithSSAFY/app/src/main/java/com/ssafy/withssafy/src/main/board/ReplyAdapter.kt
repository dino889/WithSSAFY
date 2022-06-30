package com.ssafy.withssafy.src.main.board

import android.content.Context
import android.graphics.Color
import android.view.*
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.databinding.ItemReplyListBinding
import com.ssafy.withssafy.src.dto.User
import com.ssafy.withssafy.src.dto.board.Comment

/**
 * @since 04/27/22
 * @author Jiwoo Choi
 * @param boardOrStudy true : board 댓글 // false : study 댓글
 */
class ReplyAdapter (val context: Context, val boardOrStudy: Boolean) : RecyclerView.Adapter<ReplyAdapter.ViewHolder>(){

    lateinit var commentList: MutableList<Comment>

    // 게시글 작성자
    var postUserId: Int = -1

    // 현재 로그인한 유저의 아이디
    val userId = ApplicationClass.sharedPreferencesUtil.getUser().id

    var userNum = 0

    inner class ViewHolder(private val binding: ItemReplyListBinding) : RecyclerView.ViewHolder(binding.root) {
        val nick = binding.replyItemTvUserNick
        val moreBtn = binding.replyItemIvMoreBtn

        fun bindInfo(comment: Comment) {

            binding.comment = comment

            userNum += 1

            if(comment.user != null) {
                if(boardOrStudy == true) {  // board 댓글인 경우 - 익명


                    if(comment.user.id == postUserId) {
                        nick.setTextColor(Color.parseColor("#2C64BF"))
                        nick.text = "익명(글쓴이)"
                    } else {

                        nick.text = "익명${userNum}"
                    }
                } else {    // study 댓글인 경우 실명

                    if(comment.user.id == postUserId) {
                        nick.text = "${comment.user.name}(글쓴이)"
                        nick.setTextColor(Color.parseColor("#2C64BF"))
                    } else {
                        nick.text = comment.user.name
                    }
                }
            }

            binding.executePendingBindings()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(
                    parent.context
                ), R.layout.item_reply_list, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = commentList[position]

        holder.apply {
            bindInfo(comment)
//            setIsRecyclable(false)

            moreBtn.setOnClickListener {
                val popup = PopupMenu(context, moreBtn)
                // 작성자인 경우 popup_menu_write 팝업 메뉴
                if(comment.user!!.id == userId) {
                    MenuInflater(context).inflate(R.menu.popup_menu_writer, popup.menu)

                    popup.show()
                    popup.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.modify -> {
                                modifyItemClickListener.onClick(position, comment.id, comment.user.id)
                                return@setOnMenuItemClickListener true
                            }
                            R.id.delete -> {
                                deleteItemClickListener.onClick(position, comment.id, comment.user.id)
                                return@setOnMenuItemClickListener true
                            }
                            else -> {
                                return@setOnMenuItemClickListener false
                            }
                        }
                    }
                } else {    // 작성자가 아닌 경우 popup_menu(쪽지 보내기, 신고)
                    MenuInflater(context).inflate(R.menu.popup_menu, popup.menu)

                    if(boardOrStudy == false) { //  스터디 댓글인 경우 신고 X, 신고 팝업 메뉴 숨기기
                        val popupMenu: Menu = popup.menu
                        popupMenu.findItem(R.id.report).isVisible = false
                    }

                    popup.show()
                    popup.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.sendNote -> {  // 쪽지 보내기 -> 댓글 작성자 id 필요
                                sendNoteItemClickListener.onClick(position, comment.id, comment.user.id)
                                return@setOnMenuItemClickListener true
                            }
                            R.id.report -> {    // 신고 -> 댓글 작성자 id, 댓글 id
                                reportItemClickListener.onClick(position, comment.id, comment.user.id)
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


    interface MenuClickListener {
        fun onClick(position: Int, commentId: Int, userId: Int)
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


}