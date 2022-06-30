package com.ssafy.withssafy.src.main.team

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.databinding.ItemTeamBinding
import com.ssafy.withssafy.src.dto.study.Study
import com.ssafy.withssafy.src.network.service.StudyService
import com.ssafy.withssafy.src.viewmodel.TeamViewModel
import kotlinx.coroutines.runBlocking

private const val TAG = "TeamAdapter"
class TeamAdapter(val context: Context) : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() , Filterable{
    var list = mutableListOf<Study>()
    var filteredList = list
    inner class TeamViewHolder(private val binding : ItemTeamBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(study: Study){
            binding.study = study
            if(study.user!!.id == ApplicationClass.sharedPreferencesUtil.getUser().id){
                binding.fragmentTeamMoreBtn.visibility = View.VISIBLE
            }else{
                binding.fragmentTeamMoreBtn.visibility = View.GONE
            }
            if(study.sbLimit == study.studyMembers?.size){
                binding.fragmentTeamIngOred.setChipBackgroundColorResource(R.color.grey)
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_team,parent,false))
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.apply {
            bind(filteredList[position])
            itemView.setOnClickListener {
                itemClickListener.onClick(it,position,filteredList[position].id!!)
            }
            var more = itemView.findViewById<ImageView>(R.id.fragment_team_moreBtn)
            more.setOnClickListener {
                val popup = PopupMenu(context,more)
                MenuInflater(context).inflate(R.menu.popup_menu_writer,popup.menu)
                popup.show()
                popup.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.modify ->{
                            modifyClickListener.onClick(position,filteredList[position].id!!)
                            return@setOnMenuItemClickListener true
                        }
                        R.id.delete -> {
                            deleteClickListener.onClick(position, filteredList[position].id!!)
                            return@setOnMenuItemClickListener true
                        }else ->
                            return@setOnMenuItemClickListener false
                    }
                }
            }
        }
    }
    override fun getItemCount(): Int {
        return filteredList.size
    }
    interface ItemClickListener{
        fun onClick(view: View, position: Int, id: Int)
    }
    interface DeleteClickListener{
        fun onClick(position: Int, id:Int)
    }
    interface ModifyClickListener{
        fun onClick(position:Int, id:Int)
    }
    private lateinit var deleteClickListener : DeleteClickListener
    private lateinit var itemClickListener : ItemClickListener
    private lateinit var modifyClickListener: ModifyClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }
    fun setDeleteClickListener(deleteClickListener: DeleteClickListener) {
        this.deleteClickListener = deleteClickListener
    }
    fun setModifyClickListener(modifyClickListener: ModifyClickListener){
        this.modifyClickListener = modifyClickListener
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                filteredList = if(charString.isEmpty()){
                    list
                }else{
                    val filteringList = ArrayList<Study>()
                    
                    for(item in list!!){
                        if(item.category!=null){
                            if(item!!.category.contains(charString))
                                filteringList.add(item)
                        }

                    }

                    Log.d(TAG, "performFiltering: $filteringList")
                    filteringList
                }
                val filterResult = FilterResults()
                filterResult.values = filteredList
                return filterResult
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as MutableList<Study>
                notifyDataSetChanged()
            }

        }
    }
}