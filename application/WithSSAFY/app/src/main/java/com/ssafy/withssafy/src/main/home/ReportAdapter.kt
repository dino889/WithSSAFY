package com.ssafy.withssafy.src.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.databinding.ItemReportListBinding
import com.ssafy.withssafy.src.dto.report.Report

class ReportAdapter(private val isHome: Boolean) : RecyclerView.Adapter<ReportAdapter.ReportHolder>() {

    var list = mutableListOf<Report>()

    inner class ReportHolder(private val binding: ItemReportListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(report : Report) {
            binding.report = report

            if(report.comment == null) {
                binding.reportAtHomeItemTvCategory.text = "[게시글]"
            } else if(report.board == null) {
                binding.reportAtHomeItemTvCategory.text = "[댓글]"
            }
            binding.executePendingBindings()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportAdapter.ReportHolder {
        return ReportHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_report_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ReportAdapter.ReportHolder, position: Int) {
        holder.apply {
            bind(list[position])

            itemView.setOnClickListener {
                itemClickListener.onClick(it, position, list[position].id)
            }
        }
    }

    override fun getItemCount(): Int {
        return if(list.size > 5 && isHome) {
            5
        } else {
            list.size
        }
    }

    interface ItemClickListener{
        fun onClick(view: View, position: Int, id: Int)
    }

    private lateinit var itemClickListener : ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }


}