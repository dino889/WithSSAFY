package com.ssafy.withssafy.src.main.notification

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.databinding.ItemMessageDetailTalkBinding
import com.ssafy.withssafy.src.dto.Message
import com.ssafy.withssafy.src.network.service.MessageService
import com.ssafy.withssafy.src.viewmodel.MessageViewModel
import kotlinx.coroutines.runBlocking
import retrofit2.Response

private const val TAG = "MessageDetailAdapter"
class MessageDetailAdapter(var context: Context, var messageViewModel: MessageViewModel) : RecyclerView.Adapter<MessageDetailAdapter.DetailViewHolder>() {
    var list = mutableListOf<Message>()
    var joinList = mutableListOf<Long>()
    inner class DetailViewHolder(private val binding:ItemMessageDetailTalkBinding) : RecyclerView.ViewHolder(binding.root){
        var type = itemView.findViewById<TextView>(R.id.fragment_messageDetail_type)
        fun bind(message: Message){
            binding.message = message

            if(message.u_fromId == ApplicationClass.sharedPreferencesUtil.getUser().id){
                itemView.findViewById<TextView>(R.id.fragment_messageDetail_type).text = "보낸쪽지"
                itemView.findViewById<TextView>(R.id.fragment_messageDetail_type).setTextColor(Color.parseColor("#FEC93A"))
                itemView.findViewById<ImageButton>(R.id.fragment_messageDetail_applyCheck).visibility = View.INVISIBLE

            }else{
                if(message.content.contains("지원하였습니다.")){
                    itemView.findViewById<ImageButton>(R.id.fragment_messageDetail_applyCheck).visibility = View.VISIBLE
                    var tmp = message.content.substring(5,message.content.length)
                    var id = ""
                    for(i in 0..tmp.length-1){
                        id += tmp[i]
                        if(tmp[i] == ']'){
                            break
                        }
                    }
                    id = id.substring(0,id.length-1)
                    for(item in joinList){
                        if(item.toInt() == id.toInt()){
                            Log.d(TAG, "bind: $item")
                            itemView.findViewById<ImageButton>(R.id.fragment_messageDetail_applyCheck).visibility = View.INVISIBLE
                        }
                    }
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
            itemView.setOnLongClickListener {
                showDialog(list[position],position)
                false
            }
        }
    }
    private fun showDialog(message:Message,position: Int){
        var flag  = false
        var alertDialog = AlertDialog.Builder(context)
            .setTitle("삭제")
            .setMessage("메시지를 삭제하시겠습니까?")
            .setPositiveButton("삭제", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    var response : Response<Any?>
                    runBlocking {
                        response = MessageService().deleteDetailMsg(message.id)
                    }
                    if(response.isSuccessful){
                        Toast.makeText(context, "삭제되었습니다",Toast.LENGTH_SHORT).show()
                        runBlocking {
                            messageViewModel.getMessageTalk(message.u_toId, message.u_fromId)
                        }
                        notifyItemRemoved(position)
                    }

                }
            })
            .setNeutralButton("취소", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dialog!!.dismiss()
                }
            })
            .create()
            .show()
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