package com.ssafy.withssafy.src.main.board

import android.animation.ValueAnimator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.databinding.ItemJobBinding
import com.ssafy.withssafy.src.dto.Recruit
import com.ssafy.withssafy.src.network.service.RecruitService
import kotlinx.coroutines.runBlocking

class JobAdapter(var isStudent : Boolean) : RecyclerView.Adapter<JobAdapter.JobViewHolder>(){
    var list = mutableListOf<Recruit>()
    var likeRecruitIdList = mutableListOf<Int>()
    val userId = ApplicationClass.sharedPreferencesUtil.getUser().id
    inner class JobViewHolder(private val binding:ItemJobBinding):RecyclerView.ViewHolder(binding.root){
        val heartBtn = binding.fragmentJobLike
        val moreBtn = binding.fragmentJobMore
        fun bind(recruit : Recruit){
            binding.recruit = recruit
            binding.executePendingBindings()

            if(isStudent) {
                heartBtn.visibility = View.VISIBLE
                moreBtn.visibility = View.GONE
                if(likeRecruitIdList.contains(recruit.id)) {
                    heartBtn.progress = 0.5F
                } else {
                    heartBtn.progress = 0F
                }
            } else {
                heartBtn.visibility = View.GONE
                moreBtn.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        return JobViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_job,parent,false))
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        holder.apply {
            bind(list[position])
            itemView.setOnClickListener {
                itemClickListener.onClick(it,position,list[position].id)
            }

            heartBtn.setOnClickListener{
                heartClickListener.onClick(it, position, list[position].id)
                if(heartBtn.progress > 0F){
                    heartBtn.pauseAnimation()
                    heartBtn.progress = 0F
                }else{
                    val animator = ValueAnimator.ofFloat(0f,0.5f).setDuration(500)
                    animator.addUpdateListener { animation ->
                        heartBtn.progress = animation.animatedValue as Float
                    }
                    animator.start()
                }
            }
            moreBtn.setOnClickListener{
                moreCLickListener.onClick(it, position, list[position].id)
            }
        }
    }

    interface ItemClickListener{
        fun onClick(view: View, position: Int, id: Int)
    }

    interface HeartClickListener{
        fun onClick(view: View, position: Int, id: Int)
    }

    interface MoreClickListener{
        fun onClick(view: View, position: Int, id: Int)
    }

    private lateinit var itemClickListener : ItemClickListener
    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    private lateinit var heartClickListener: HeartClickListener
    fun setHeartClickListener(heartClickListener: HeartClickListener) {
        this.heartClickListener = heartClickListener
    }

    private lateinit var moreCLickListener : MoreClickListener
    fun setMoreClickListener(moreClickListener: MoreClickListener) {
        this.moreCLickListener = moreClickListener
    }

    override fun getItemCount(): Int {
        return list.size
    }
}