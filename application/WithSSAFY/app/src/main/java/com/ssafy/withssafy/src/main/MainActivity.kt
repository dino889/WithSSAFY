package com.ssafy.withssafy.src.main

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseActivity
import com.ssafy.withssafy.databinding.ActivityMainBinding
import com.ssafy.withssafy.src.dto.Message
import com.ssafy.withssafy.src.dto.report.Report
import com.ssafy.withssafy.src.dto.report.ReportRequest
import com.ssafy.withssafy.src.login.SingInActivity
import com.ssafy.withssafy.src.main.board.BoardDetailAdapter
import com.ssafy.withssafy.src.main.board.CommentAdapter
import com.ssafy.withssafy.src.main.home.ReportAdapter
import com.ssafy.withssafy.src.network.service.BoardService
import com.ssafy.withssafy.src.network.service.CommentService
import com.ssafy.withssafy.src.network.service.MessageService
import com.ssafy.withssafy.src.network.service.ReportService
import com.ssafy.withssafy.src.viewmodel.*
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import retrofit2.Response

private const val TAG = "MainActivity_ws"
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private lateinit var bottomNavigation: BottomNavigationView
    private val STORAGE = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private val STORAGE_CODE = 99
    private val NOTICE_CODE = 100
    private val BOARD_CODE = 101
    private val teamViewModel : TeamViewModel by viewModels()
    private val noticeViewModel : NoticeViewModel by viewModels()
    private val boardViewModel : BoardViewModel by viewModels()
    private val homeViewModel : HomeViewModel by viewModels()
    private val userViewModel : UserViewModel by viewModels()

    private val userId = ApplicationClass.sharedPreferencesUtil.getUser().id


    // 권한 허가
    var permissionListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() { // 권한 허가시 실행 할 내용
//            openGallery()
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            showCustomToast("Permission Denied")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavigation()
    }

    /**
     * bottom Navi 초기화
     */
    private fun initNavigation(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainActivity_navHost) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.mainActivityBottomNav, navController)
    }

    /**
     * BottomNavigation show/hide 설정
     * true - hide
     * false - show
     */
    fun hideBottomNavi(state : Boolean) {
        if(state) {
            binding.mainActivityBottomNav.visibility = View.GONE
        } else {
            binding.mainActivityBottomNav.visibility = View.VISIBLE
        }
    }


    /**
     * read gallery 권한 체크
     */
    fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= 26) { // 출처를 알 수 없는 앱 설정 화면 띄우기
            val pm: PackageManager = this@MainActivity.packageManager
            if (!pm.canRequestPackageInstalls()) {
                startActivity(
                    Intent(
                        Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                        Uri.parse("package:${context?.packageName}")
                    )
                )
            }
        }

        if (Build.VERSION.SDK_INT >= 23) { // 마시멜로(안드로이드 6.0) 이상 권한 체크
            TedPermission.create()
                .setPermissionListener(permissionListener)
                .setRationaleMessage("앱을 이용하기 위해서는 접근 권한이 필요합니다")
                .setDeniedMessage("If you reject permission,you can not use this service\n" +
                        "\n\nPlease turn on permissions at [Setting] > [Permission] ")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE).check()
        }
//        else {
//            selectImg()
//        }
    }
    fun checkPermission(permissions: Array<out String>, type: Int): Boolean
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, permissions, type)
                    return false
                }
            }
        }
        return true
    }
    fun openGallery(code:Int) {
        if(checkPermission(STORAGE,STORAGE_CODE)){
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
//            filterActivityLauncher.launch(intent)
            startActivityForResult(intent,code)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                STORAGE_CODE -> {
                    teamViewModel.uploadImageUri = data?.data
//                    mainViewModels.uploadedImageUri = data?.data
//
//                    Log.d(TAG, "onActivityResult: ${data?.data}")
//                    // 이미지 검사
//                    if(mainViewModels.uploadedImageUri == null) showCustomToast("이미지가 정상적으로 로드 되지 않았습니다.")
//                    else {
//                        mainViewModels.uploadedImage = MediaStore.Images.Media.getBitmap(contentResolver, mainViewModels.uploadedImageUri)
//                        checkTheType()
//                        photoDialog.dismiss()
//                    }
                }
                NOTICE_CODE -> {
                    noticeViewModel.setUploadImageUri(data?.data!!)
                    //noticeViewModel.uploadImageUri = data?.data
                    Log.d(TAG, "onActivityResult: NOTICE ${data?.data}")
                }
                BOARD_CODE -> {
                    boardViewModel.setBoardImgUri(data?.data!!)

                }
            }
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            STORAGE_CODE -> {
                for (grant in grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        showCustomToast("저장소 권한을 승인해 주세요.")
                    }
                }
            }
        }
    }

    /**
     * 갤러리 사진 선택 result
     */
    private val filterActivityLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it ->
            if(it.resultCode == AppCompatActivity.RESULT_OK && it.data != null) {
                val currentImageUri = it.data?.data

                try {
                    currentImageUri?.let { uri ->
                        boardViewModel.setBoardImgUri(uri)
//                        fileExtension = requireActivity().contentResolver.getType(currentImageUri)
//                        fileExtension = fileExtension!!.substring(fileExtension!!.lastIndexOf("/") + 1, fileExtension!!.length)
                    }
                }catch(e:Exception) {
                    e.printStackTrace()
                }
            } else if(it.resultCode == AppCompatActivity.RESULT_CANCELED){
                boardViewModel.setBoardImgUri(Uri.EMPTY)
                showCustomToast("사진 선택 취소")
            } else{
                Log.d(TAG,"filterActivityLauncher 실패")
            }
        }

    fun logout() {
        ApplicationClass.sharedPreferencesUtil.deleteUser()
        ApplicationClass.sharedPreferencesUtil.deleteUserCookie()
        ApplicationClass.sharedPreferencesUtil.deleteAutoLogin()

        val intent = Intent(this, SingInActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
    fun showDialogSendMessage(toId:Int,fromId:Int) : Boolean{
        var flag = false
        var dialog = Dialog(this)
        var dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_send_message,null)
        if(dialogView.parent!=null){
            (dialogView.parent as ViewGroup).removeView(dialogView)
        }
        dialog.setContentView(dialogView)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        var params = dialog.window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog.window?.attributes = params
        dialog.show()
        dialogView.findViewById<ImageButton>(R.id.fragment_messageDetail_dialog_appBarPrev).setOnClickListener {
            dialog.dismiss()
        }
        dialogView.findViewById<TextView>(R.id.fragment_messageDetail_dialog_insert).setOnClickListener {
            var message : Message
            if(fromId == ApplicationClass.sharedPreferencesUtil.getUser().id){
                message = Message(
                    dialogView.findViewById<EditText>(R.id.fragment_messageDetail_dialog_sendMsgContent).text.toString(),
                    0,
                    fromId,
                    toId
                )
            }else{
                message = Message(
                    dialogView.findViewById<EditText>(R.id.fragment_messageDetail_dialog_sendMsgContent).text.toString(),
                    0,
                    toId,
                    fromId
                )
            }

            runBlocking {
                val response = MessageService().insertMessage(message)
                if(response.code() == 204){
                    dialog.dismiss()
                    MessageViewModel().getMessageTalk(ApplicationClass.sharedPreferencesUtil.getUser().id, fromId)
//                    detailAdapter.notifyDataSetChanged()
                    flag = true
                }
            }
        }
        if(flag){
            return true
        }
        return false
    }

    /**
     * 게시글 신고 다이얼로그
     * @param postOrComment true : 게시글 신고, false : 댓글 신고
     * @param boardTypeId -1 : userWrotePostList / -2 : userPostListOnComment / -3 : userLikePostList / -4 : hotPostList / 100 : boardListByType
     * @param detailChk true : BoardDetail에서 신고된 게시글, false : PostDetail에서 신고된 게시글
     */
    fun showReportDialog(id: Int, postOrComment: Boolean, reportAdapter: ReportAdapter?, commentAdapter: CommentAdapter?, boardDetailAdapter: BoardDetailAdapter?, boardTypeId: Int, detailChk: Boolean) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_report,null)

//        if(dialogView.parent != null){
//            (dialogView.parent as ViewGroup).removeAllViews()
//        }

        val dialog = Dialog(this)
        dialog.setContentView(dialogView)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()

        dialogView.findViewById<Button>(R.id.reportDialog_btnCancel).setOnClickListener {
            dialog.dismiss()
        }

        dialogView.findViewById<AppCompatButton>(R.id.reportDialog_btnReport).setOnClickListener {
            val content = dialogView.findViewById<TextView>(R.id.reportDialog_tvContent).text.toString()

            if(content.trim().isNotEmpty()) {
                val report : ReportRequest
                if(postOrComment == true) { // 게시글 신고인 경우
                    report = ReportRequest(id = 0, board = id, comment = null, content = content, user = userId)
                } else {    // 댓글 신고인 경우
                    report = ReportRequest(id = 0, board = null, comment = id, content = content, user = userId)
                }

                try {
                    var reportAllow = false // 신고 가능 여부 확인

                    var dupChkResponse : Response<List<Int>>
                    runBlocking {
                        dupChkResponse = if(postOrComment == true) {
                            ReportService().getUserListByBoardId(id)
                        } else {
                            ReportService().getUserListByCommentId(id)
                        }

                        if(dupChkResponse.isSuccessful) {
                            val res = dupChkResponse.body()
                            if(res != null) {
                                if(res.isEmpty()) {
                                    reportAllow = true
                                } else {
                                    for (reporterId in res) {
                                        if(reporterId == userId) {  // 해당 게시글에 로그인한 유저가 신고한 내역이 있으면
                                            reportAllow = false
                                            showCustomToast("해당 글에 신고를 접수한 내역이 있습니다.")
                                            break
                                        }
                                    }
                                }
                            } else {
                                Log.e(TAG, "showReportDialog: ${dupChkResponse.message()}", )
                            }
                        } else {
                            Log.e(TAG, "showReportDialog: ${dupChkResponse.message()}", )
                        }
                    }

                    if(reportAllow) {
                        var response : Response<List<Report>>
                        runBlocking {
                            response = ReportService().addReport(report)
                        }
                        if(response.isSuccessful) {
                            val res = response.body()
                            if(res != null) {
                                if(res.size < 4) {    // 신고 횟수가 4회 미만
                                    showCustomToast("신고가 접수되었습니다.\n관리자 확인 후 처리될 예정입니다.")
                                } else {
                                    val firstReport = res[0]
                                    if(firstReport.comment != null) { // 댓글 신고 횟수 4회 이상 - 해당 댓글 삭제
                                        var deleteCmtResponse : Response<Any?>

                                        runBlocking {
                                            deleteCmtResponse = CommentService().deleteComment(firstReport.comment.id)
                                        }
                                        runBlocking {
                                            homeViewModel.getReportList()
                                        }

                                        if(deleteCmtResponse.isSuccessful) {
                                            showCustomToast("누적된 신고 횟수가 기준치를 초과하였기에 해당 댓글은 삭제 처리 되었습니다.")
                                            runBlocking {
                                                boardViewModel.getCommentList(firstReport.comment.boardId)
                                            }
                                        }
                                    } else if(firstReport.board != null) {    // 게시글 신고 횟수 4회 이상 - 해당 게시글 삭제
                                        var deletePostResponse : Response<Any?>

                                        runBlocking {
                                            deletePostResponse = BoardService().deletePost(firstReport.board.id)
                                            homeViewModel.getReportList()
                                        }


                                        if(deletePostResponse.isSuccessful) {
                                            showCustomToast("누적된 신고 횟수가 기준치를 초과하였기에 해당 게시글은 삭제 처리 되었습니다.")
                                            if(detailChk == true) { // boardDetail
                                                runBlocking {
                                                    boardViewModel.getUserLikePostList(userId)
                                                }
                                                when(boardTypeId) {
                                                    -2 -> {
                                                        runBlocking {
                                                            boardViewModel.getUserPostListOnComment(userId)
                                                        }
                                                    }
                                                    -4 -> {
                                                        runBlocking {
                                                            boardViewModel.getHotPostList()
                                                        }
                                                    }
                                                    else -> {
                                                        runBlocking {
                                                            boardViewModel.getBoardListByType(firstReport.board.boardType.id)
                                                        }
                                                    }
                                                }
                                                if(boardDetailAdapter != null) {
                                                    boardDetailAdapter.notifyDataSetChanged()
                                                }
                                            } else {
                                                this.onBackPressed()
                                            }
                                        }
                                    }

                                    if(reportAdapter != null) {
                                        reportAdapter.notifyDataSetChanged()
                                    }

                                    if(commentAdapter != null) {
                                        commentAdapter.notifyDataSetChanged()
                                    }
                                }
                            }
                        } else {
                            Log.e(TAG, "report: 통신 실패", )
                        }
                    }
                    dialog.dismiss()
                } catch (e: HttpException) {
                    Log.e(TAG, "report ${e.message()}", )
                }

            } else {
                showCustomToast("신고 사유를 작성해 주세요.")
            }
        }
    }

}
