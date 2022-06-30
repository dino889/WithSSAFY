package com.ssafy.withssafy.src.main.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import com.ssafy.withssafy.R

class BannerViewPagerAdapter(var context:Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    companion object {
        const val ITEM_COUNT = 3
    }

    private var bannerItemList: List<Int>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BannerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout_banner, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return ITEM_COUNT
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        bannerItemList?.let{ bannerItemList ->
            (holder as BannerViewHolder).bind(bannerItemList[position])

        }
    }

    fun submitList(list: List<Int>?) {
        bannerItemList = list
        notifyDataSetChanged()
    }

    class BannerViewHolder
    constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(bannerItem: Int) {
            var imageView = itemView.findViewById<ImageView>(R.id.iv_banner_image)
            Glide.with(context)
                .load(bannerItem)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                .into(imageView)

//            itemView.findViewById<ImageView>(R.id.iv_banner_image).setImageResource(bannerItem)
        }
    }
}