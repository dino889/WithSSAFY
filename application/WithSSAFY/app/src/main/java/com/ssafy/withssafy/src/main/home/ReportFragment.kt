package com.ssafy.withssafy.src.main.home

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
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gun0912.tedpermission.provider.TedPermissionProvider
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.DialogReportDetailBinding
import com.ssafy.withssafy.databinding.FragmentReportBinding
import com.ssafy.withssafy.src.dto.FcmRequest
import com.ssafy.withssafy.src.dto.report.Report
import com.ssafy.withssafy.src.dto.report.ReportRequest
import com.ssafy.withssafy.src.main.MainActivity
import com.ssafy.withssafy.src.main.board.CommentAdapter
import com.ssafy.withssafy.src.network.service.BoardService
import com.ssafy.withssafy.src.network.service.CommentService
import com.ssafy.withssafy.src.network.service.FcmService
import com.ssafy.withssafy.src.network.service.ReportService
import com.ssafy.withssafy.util.CommonUtils
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception

class ReportFragment : BaseFragment<FragmentReportBinding>(FragmentReportBinding::bind, R.layout.fragment_report) {
    private val TAG = "ReportFragment_싸피"
    private lateinit var mainActivity: MainActivity

    private lateinit var reportDetailAdapter: ReportDetailAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onResume() {
        super.onResume()
        mainActivity.hideBottomNavi(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.hideBottomNavi(true)

        runBlocking {
            homeViewModel.getReportList()
        }
        initListener()
        initAdapter()
    }

    private fun initListener() {
        binding.reportFragmentAppBarPrev.setOnClickListener {
            this@ReportFragment.findNavController().popBackStack()
        }
    }

    private fun initAdapter() {
        reportDetailAdapter = ReportDetailAdapter()
        homeViewModel.reportList.observe(viewLifecycleOwner) {
            reportDetailAdapter.list = it
        }
        binding.reportFragmentRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = reportDetailAdapter
        }
        binding.reportFragmentRv.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        reportDetailAdapter.setItemClickListener(object : ReportDetailAdapter.ItemClickListener {
            override fun onClick(view: View, report: Report, position: Int) {
                showReportHandlingDialog(report, position)
            }

        })
    }


    private fun showReportHandlingDialog(report: Report, position: Int) {
        val dialogView = LayoutInflater.from(TedPermissionProvider.context).inflate(R.layout.dialog_report_detail,null)

        if(dialogView.parent != null){
            (dialogView.parent as ViewGroup).removeAllViews()
        }

        val dialog = Dialog(requireContext())
        dialog.setContentView(dialogView)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val params = dialog.window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        dialog.window?.attributes = params

        var boardOrComment = true // true : 게시글, false : 댓글

        dialogView.findViewById<TextView>(R.id.reportDetailDialog_tvReportUserName).text = report.user.name // 신고자 이름
        dialogView.findViewById<TextView>(R.id.reportDetailDialog_tvReportDate).text = CommonUtils.unixTimeToDateFormatInBoard(report.write_dt.toLong()) // 신고 시간
        dialogView.findViewById<TextView>(R.id.reportDetailDialog_tvReportContent).text = report.content

        val titleLayout = dialogView.findViewById<ConstraintLayout>(R.id.reportDetailDialog_clBoardTitle)
        val title = dialogView.findViewById<TextView>(R.id.reportDetailDialog_tvBoardTitle)
        val content = dialogView.findViewById<TextView>(R.id.reportDetailDialog_tvBoardContent)

        if(report.comment != null) {    // comment 신고인 경우
            titleLayout.visibility = View.GONE
            content.text = report.comment.content
            boardOrComment = false

        } else if(report.board != null) {
            titleLayout.visibility = View.VISIBLE
            title.text = report.board.title
            content.text = report.board.content
            boardOrComment = true
        }

        dialog.show()

        dialogView.findViewById<AppCompatButton>(R.id.reportDetailDialog_btnCancel).setOnClickListener { // 신고 반려 -> report delete
            var response : Response<Report>
            runBlocking {
                response = ReportService().deleteReport(report.id)
            }

            if(response.isSuccessful) {
                val res = response.body()
                if(res != null) {
                    showCustomToast("신고 내역이 삭제되었습니다.")
                    runBlocking {
                        homeViewModel.getReportList()
                    }
                    reportDetailAdapter.notifyItemRemoved(position)

                    try {
                        val fcmResponse : Response<Any?>

                        runBlocking {
                            if(boardOrComment) {    // board
                                fcmResponse = FcmService().sendToMsg(FcmRequest(type = 2, title = "신고 접수 처리", body = "[${CommonUtils.ellipsisContent(report.board!!.title)}] 게시글에 대해 접수한 신고 내역이 반려되었습니다."), report.user.deviceToken)
                            } else {
                                fcmResponse = FcmService().sendToMsg(FcmRequest(type = 2, title = "신고 접수 처리", body = "[${CommonUtils.ellipsisContent(report.comment!!.content)}] 댓글에 대해 접수한 신고 내역이 반려되었습니다."), report.user.deviceToken)
                            }
                        }

                        if(fcmResponse.isSuccessful) {
                            Log.d(TAG, "showReportHandlingDialog: $fcmResponse")
                        } else {
                            Log.e(TAG, "showReportHandlingDialog: ${fcmResponse.message()}")
                        }
                    } catch (e : Exception) {
                        Log.e(TAG, "showReportHandlingDialog: ${e.printStackTrace()}", )
                    }

                }
            }

            dialog.dismiss()
        }

        dialogView.findViewById<AppCompatButton>(R.id.reportDetailDialog_btnReportConfirm).setOnClickListener { // 신고 접수 -> 해당 게시글 및 댓글 삭제

            try {
                if(boardOrComment == true && report.board != null) { // 게시글 신고인 경우
                    var deletePostResponse : Response<Any?>

                    runBlocking {
                        deletePostResponse = BoardService().deletePost(report.board.id)
                    }

                    if(deletePostResponse.isSuccessful) {
                        showCustomToast("해당 게시글이 삭제되었습니다.")
                        runBlocking {
                            homeViewModel.getReportList()
                        }
                        reportDetailAdapter.notifyDataSetChanged()
                        try {
                            val fcmResponse : Response<Any?>

                            runBlocking {
                                fcmResponse = FcmService().sendToMsg(FcmRequest(type = 2, title = "신고 접수 처리", body = "[${CommonUtils.ellipsisContent(report.board.title)}] 접수하신 신고 내역에 대해 해당 게시글은 처리대상 게시글로 판단되어 삭제 처리 되었습니다."), report.user.deviceToken)
                            }

                            if(fcmResponse.isSuccessful) {
                                Log.d(TAG, "showReportHandlingDialog: $fcmResponse")
                            } else {
                                Log.e(TAG, "showReportHandlingDialog: ${fcmResponse.message()}")
                            }
                        } catch (e : Exception) {
                            Log.e(TAG, "showReportHandlingDialog: ${e.printStackTrace()}", )
                        }

                    } else {
                        showCustomToast("게시글 삭제 통신 오류")
                        Log.e(TAG, "showReportHandlingDialog: ${deletePostResponse.message()}", )
                    }

                } else if(boardOrComment == false && report.comment != null) {    // 댓글 신고인 경우
                    var deleteCmtResponse : Response<Any?>

                    runBlocking {
                        deleteCmtResponse = CommentService().deleteComment(report.comment.id)
                    }

                    if(deleteCmtResponse.isSuccessful) {
                        showCustomToast("해당 댓글이 삭제되었습니다.")
                        runBlocking {
                            homeViewModel.getReportList()
                        }
                        reportDetailAdapter.notifyDataSetChanged()
                        try {
                            val fcmResponse : Response<Any?>

                            runBlocking {
                                fcmResponse = FcmService().sendToMsg(FcmRequest(type = 2, title = "신고 접수 처리", body = "[${CommonUtils.ellipsisContent(report.comment.content)}] 접수하신 신고 내역에 대해 해당 댓글은 처리대상 댓글로 판단되어 삭제 처리 되었습니다."), report.user.deviceToken)
                            }

                            if(fcmResponse.isSuccessful) {
                                Log.d(TAG, "showReportHandlingDialog: $fcmResponse")
                            } else {
                                Log.e(TAG, "showReportHandlingDialog: ${fcmResponse.message()}")
                            }
                        } catch (e : Exception) {
                            Log.e(TAG, "showReportHandlingDialog: ${e.printStackTrace()}", )
                        }
                    } else {
                        showCustomToast("댓글 삭제 통신 오류")
                        Log.e(TAG, "showReportHandlingDialog: ${deleteCmtResponse.message()}", )
                    }
                }
                dialog.dismiss()
            } catch (e: HttpException) {
                Log.e(TAG, "report ${e.message()}", )
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivity.hideBottomNavi(false)
    }
}