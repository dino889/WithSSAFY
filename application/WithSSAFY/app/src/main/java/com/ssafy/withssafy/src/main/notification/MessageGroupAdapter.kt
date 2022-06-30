package com.ssafy.withssafy.src.main.notification

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.databinding.ItemMessageGroupBinding
import com.ssafy.withssafy.src.dto.Message
import com.ssafy.withssafy.src.network.service.MessageService
import kotlinx.coroutines.runBlocking
import retrofit2.Response

private const val TAG = "MessageGroupAdapter"
class MessageGroupAdapter  : RecyclerView.Adapter<MessageGroupAdapter.GroupViewHolder>(){
    var list = mutableListOf<Message>()
    inner class GroupViewHolder(private val binding:ItemMessageGroupBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message){
            binding.message = message
            itemView.findViewById<TextView>(R.id.fragment_message_group_delete).setOnClickListener {
                removeData(this.layoutPosition, message.u_fromId, message.u_toId)
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        return GroupViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_message_group,parent,false))
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.apply {
            bind(list[position])
            itemView.findViewById<ConstraintLayout>(R.id.swipe_messageGroup_list).setOnClickListener {
                itemClickListener.onClick(it,position,list[position].u_fromId,list[position].u_toId)
            }
        }
    }
    fun removeData(position: Int, id1:Int, id2: Int){
        deleteGroup(id1, id2)
        list.removeAt(position)
        notifyItemRemoved(position)
    }
    private fun deleteGroup(id1:Int, id2:Int){
        var response : Response<Any?>
        runBlocking {
            response = MessageService().deleteGroupMsg(id1, id2)
        }
        if(response.isSuccessful){
            Log.d(TAG, "deleteGroup: 삭제완료")
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
    interface ItemClickListener{
        fun onClick(view: View, position:Int, fromId:Int,toId:Int)
    }
    private lateinit var itemClickListener : ItemClickListener
    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }
}