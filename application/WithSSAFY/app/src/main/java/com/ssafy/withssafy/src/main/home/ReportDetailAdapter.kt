package com.ssafy.withssafy.src.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.databinding.ItemReportDetailListBinding
import com.ssafy.withssafy.databinding.ItemReportListBinding
import com.ssafy.withssafy.src.dto.report.Report

class ReportDetailAdapter() : RecyclerView.Adapter<ReportDetailAdapter.ReportHolder>() {

    lateinit var list: MutableList<Report>

    inner class ReportHolder(private val binding: ItemReportDetailListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(report : Report) {
            binding.report = report

            if(report.comment == null) {
                binding.reportDetailItemTvType.text = "게시글"
            } else if(report.board == null) {
                binding.reportDetailItemTvType.text = "댓글"
            }

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportDetailAdapter.ReportHolder {
        return ReportHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_report_detail_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ReportDetailAdapter.ReportHolder, position: Int) {
        val item = list[position]
        holder.apply {
            bind(item)

            itemView.setOnClickListener {
                itemClickListener.onClick(it, item, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface ItemClickListener{
        fun onClick(view: View, report: Report, position: Int)
    }

    private lateinit var itemClickListener : ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

}