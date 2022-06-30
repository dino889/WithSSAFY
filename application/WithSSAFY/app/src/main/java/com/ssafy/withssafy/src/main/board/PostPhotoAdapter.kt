package com.ssafy.withssafy.src.main.board

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.databinding.ItemPhotoListBinding
import java.lang.Exception

class PostPhotoAdapter() : RecyclerView.Adapter<PostPhotoAdapter.BaseViewHolder>() {
    private val TAG = "PostPhotoAdapter_ws"
    val ITEM = 1
    val HEADER = 0
    var photoList = mutableListOf<Uri>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
            when(viewType){
                HEADER -> {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.footer_add_photo, parent, false)
                    HeaderViewHolder(view)
                }
                ITEM -> {
                    ItemViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_photo_list, parent, false))
                }
                else -> throw Exception("$viewType")

            }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
            if(holder is HeaderViewHolder){
                holder.itemView.findViewById<MaterialCardView>(R.id.addPhotoFooter).setOnClickListener {
                    addClickListener.onClick(it, position)
                }
            } else if(holder is ItemViewHolder) {
                holder.onBind(photoList[position - 1])
            }
    }

    override fun getItemCount(): Int {
        return photoList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0) {
            HEADER
        } else {
            ITEM
        }
    }

    open class BaseViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView)

    class HeaderViewHolder(itemView:View):BaseViewHolder(itemView)

    inner class ItemViewHolder(private val binding: ItemPhotoListBinding):BaseViewHolder(binding.root) {

        fun onBind(photo: Uri){
            // data binding 하는 코드로 바꿔야함.
            binding.photoListItemIvPhoto.setImageURI(photo)
        }
    }


    interface AddClickListener{
        fun onClick(view:View, position:Int)
    }

    private lateinit var addClickListener: AddClickListener

    fun setAddClickListener(addClickListener: AddClickListener){
        this.addClickListener = addClickListener
    }

//    interface ItemClickListener{
//        fun onClick(view: View, position: Int)
//    }
//
//    private lateinit var itemClickListener : ItemClickListener
//
//    fun setItemClickListener(itemClickListener: ItemClickListener){
//        this.itemClickListener = itemClickListener
//    }
}