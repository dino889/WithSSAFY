package com.ssafy.withssafy.src.main.notification

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentMessageDetailBinding
import com.ssafy.withssafy.src.dto.Message
import com.ssafy.withssafy.src.dto.study.StudyMemberRequest
import com.ssafy.withssafy.src.main.MainActivity
import com.ssafy.withssafy.src.network.service.MessageService
import com.ssafy.withssafy.src.network.service.StudyService
import kotlinx.coroutines.runBlocking

private const val TAG = "MessageDetailFragment"
class MessageDetailFragment : BaseFragment<FragmentMessageDetailBinding>(FragmentMessageDetailBinding::bind,R.layout.fragment_message_detail) {
    private var fromId = 0
    private var toId = 0
    private lateinit var detailAdapter: MessageDetailAdapter
    private lateinit var mainActivity:MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fromId = it.getInt("fromId")
            toId = it.getInt("toId")
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = messageViewModel
        runBlocking {
            if(fromId == ApplicationClass.sharedPreferencesUtil.getUser().id){
                messageViewModel.getMessageTalk(toId, ApplicationClass.sharedPreferencesUtil.getUser().id)
                messageViewModel.getJoinList(toId, ApplicationClass.sharedPreferencesUtil.getUser().id)
            }else{
                messageViewModel.getMessageTalk(ApplicationClass.sharedPreferencesUtil.getUser().id, fromId)
                messageViewModel.getJoinList(ApplicationClass.sharedPreferencesUtil.getUser().id,fromId)
            }

        }
        setListener()
    }
    private fun setListener(){
        initButtons()
        initAdapter()
    }
    private fun initButtons(){
        binding.fragmentMessageDetailAppBarPrev.setOnClickListener {
            this@MessageDetailFragment.findNavController().popBackStack()
        }
        binding.fragmentMessageDetailSendMsg.setOnClickListener {
            if(mainActivity.showDialogSendMessage(toId, fromId)){
                detailAdapter.notifyDataSetChanged()
            }
        }
    }
    private fun initAdapter(){
        detailAdapter = MessageDetailAdapter(requireContext(),messageViewModel)
        messageViewModel.messageTalk.observe(viewLifecycleOwner){
            Log.d(TAG, "initAdapter: $it")
            detailAdapter.list = it
        }
        messageViewModel.joinList.observe(viewLifecycleOwner){
            Log.d(TAG, "initAdapter: $it")
            detailAdapter.joinList = it
        }
        binding.fragmentMessgaeDetailRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            adapter = detailAdapter
        }
        detailAdapter.setItemClickListener(object: MessageDetailAdapter.ItemClickListener {
            override fun onClick(
                view: View,
                position: Int,
                toId: Int,
                fromId: Int,
                StudyId: Int,
                StudyTitle: String
            ) {
                showRequestDialog(toId, fromId, StudyId, StudyTitle)
            }
        })
    }
    private fun showRequestDialog(toId:Int, fromId:Int, StudyId:Int, StudyTitle:String){
        var dialog = Dialog(requireContext())
        var dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_team_request,null)
        if(dialogView.parent!=null){
            (dialogView.parent as ViewGroup).removeView(dialogView)
        }
        dialog.setContentView(dialogView)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        runBlocking {
            userViewModel.getUser(fromId,2)
        }
        dialogView.findViewById<TextView>(R.id.fragment_team_requestStudyName).text = "[${StudyTitle}] 익명${fromId}님"
        dialogView.findViewById<TextView>(R.id.textView12).text = "의 지원을 수락하시겠습니까?"
        dialogView.findViewById<AppCompatButton>(R.id.fragment_team_requestRequst).text = "수락"
        dialogView.findViewById<AppCompatButton>(R.id.fragment_team_requestRequst).setOnClickListener {
            runBlocking {
                teamViewModel.getStudy(StudyId)
            }
            var member = teamViewModel.study.value!!.studyMembers
            if (member != null) {
                for(item in member){
                    if(item.id == fromId){
                        showCustomToast("이미 추가된 사용자입니다.")
                        dialog.dismiss()
                    }
                }
            }
            var studyMember = StudyMemberRequest(
                fromId
            )
            runBlocking {
                val response = StudyService().joinStudy(StudyId,studyMember)
                if(response.code() == 204){
                    Log.d(TAG, "showRequestDialog: 조인성공")
                    var message = Message(
                        "[스터디 ${StudyId}] '${StudyTitle}’의 지원을 수락하였습니다.",
                        0,
                        ApplicationClass.sharedPreferencesUtil.getUser().id,
                        fromId
                    )
                    var response2 = MessageService().insertMessage(message)
                    if(response2.isSuccessful){
                        runBlocking {
                            if(fromId == ApplicationClass.sharedPreferencesUtil.getUser().id){
                                messageViewModel.getMessageTalk(toId, ApplicationClass.sharedPreferencesUtil.getUser().id)
                            }else{
                                messageViewModel.getMessageTalk(ApplicationClass.sharedPreferencesUtil.getUser().id, fromId)
                            }
                            detailAdapter.notifyDataSetChanged()
                        }
                    }
                   dialog.dismiss()
                }else{
                    Log.d(TAG, "showRequestDialog: ${response.code()}")
                }
            }
        }
        dialogView.findViewById<AppCompatButton>(R.id.fragment_team_requestCancle).setOnClickListener {
            dialog.dismiss()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MessageDetailFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}