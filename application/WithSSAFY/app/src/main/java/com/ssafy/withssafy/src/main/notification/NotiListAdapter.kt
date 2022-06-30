package com.ssafy.withssafy.src.main.notification

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.databinding.RecyclerviewNotiListItemBinding
import com.ssafy.withssafy.src.dto.Notification
import com.ssafy.withssafy.src.network.service.NotificationService
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import java.util.*

private const val TAG = "NotiListAdapter"
class NotiListAdapter(val context: Context) : RecyclerView.Adapter<NotiListAdapter.NotiListHolder>() {
    var list = mutableListOf<Notification>()
    inner class NotiListHolder(private val binding: RecyclerviewNotiListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var notiIcon = binding.rvItemIvNotiListIcon
        fun bind(noti : Notification) {
            binding.noti = noti
            if(noti.type == 1) {
                notiIcon.setImageResource(R.drawable.alert)
            } else if(noti.type == 2) {
                notiIcon.setImageResource(R.drawable.user2)
            } else if(noti.type == 3) {
                notiIcon.setImageResource(R.drawable.work)
            }
            itemView.findViewById<TextView>(R.id.rv_item_tv_noti_list_delete).setOnClickListener {
                removeData(this.layoutPosition, noti.id)
            }
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotiListAdapter.NotiListHolder {
        return NotiListHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.recyclerview_noti_list_item,parent,false))
    }

    override fun onBindViewHolder(holder: NotiListHolder, position: Int) {
        holder.apply {
            bind(list[position])
        }
    }

    fun removeData(position: Int, id: Int) {
        Log.d(TAG, "removeData: $position, $id")
        deleteNoti(id)
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    private fun deleteNoti(id: Int) {
        var response: Response<Any?>
        runBlocking {
            response = NotificationService().deleteNotificationById(id)
        }
        if(response.code() == 204) {
            Toast.makeText(context, "알림을 삭제했습니다.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "알림 삭제를 실패했습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}