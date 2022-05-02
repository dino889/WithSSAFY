package com.ssafy.withssafy.src.main.notification

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.databinding.ItemMessageDetailTalkBinding
import com.ssafy.withssafy.src.dto.Message

private const val TAG = "MessageDetailAdapter"
class MessageDetailAdapter() : RecyclerView.Adapter<MessageDetailAdapter.DetailViewHolder>() {
    var list = mutableListOf<Message>()
    inner class DetailViewHolder(private val binding:ItemMessageDetailTalkBinding) : RecyclerView.ViewHolder(binding.root){
        var type = itemView.findViewById<TextView>(R.id.fragment_messageDetail_type)
        fun bind(message: Message){
            binding.message = message
            if(message.u_fromId == ApplicationClass.sharedPreferencesUtil.getUser().id){
                itemView.findViewById<TextView>(R.id.fragment_messageDetail_type).text = "보낸쪽지"
            }else{
                if(message.content.contains("지원하였습니다.")){
                    itemView.findViewById<ImageButton>(R.id.fragment_messageDetail_applyCheck).visibility = View.VISIBLE
                }
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_message_detail_talk,parent,false))
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.apply {
            bind(list[position])
            if(list[position].content.contains("지원하였습니다")){
                var tmp = ""
                var idxTmp = 0
                Log.d(TAG, "onBindViewHolder: ${list[position].content}")
                for(i in 4..list[position].content.length-1){

                    if(list[position].content[i] == ']'){
                        idxTmp = i+2;
                        break
                    }
                    tmp += list[position].content[i]
                }
                var tmpTitle = ""
                for(i in idxTmp+1..list[position].content.length-1){
                    if(list[position].content[i] == '’') {
                        break
                    }
                    tmpTitle += list[position].content[i]
                }
                var studyId = tmp.trim().toInt()
                Log.d(TAG, "onBindViewHolder: $studyId  $tmpTitle")
                itemView.findViewById<ImageButton>(R.id.fragment_messageDetail_applyCheck).setOnClickListener {
                    itemClickListener.onClick(it,position,list[position].u_toId,list[position].u_fromId,studyId,tmpTitle)
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    interface ItemClickListener {
        fun onClick(view: View, position:Int, toId:Int, fromId:Int, StudyId:Int, StudyTitle:String)
    }
    private lateinit var itemClickListener: ItemClickListener
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }
}