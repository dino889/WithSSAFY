package com.ssafy.withssafy.src.login

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.WindowManager
import androidx.activity.viewModels
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseActivity
import com.ssafy.withssafy.databinding.ActivitySingInBinding
import com.ssafy.withssafy.src.dto.User
import com.ssafy.withssafy.src.main.MainActivity
import com.ssafy.withssafy.src.network.api.FCMApi
import com.ssafy.withssafy.src.network.api.UserApi
import com.ssafy.withssafy.src.viewmodel.UserViewModel
import com.ssafy.withssafy.util.RetrofitUtil
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import retrofit2.Retrofit
import java.security.DigestException
import java.security.MessageDigest

private const val TAG = "SingInActivity_싸피"
class SingInActivity : BaseActivity<ActivitySingInBinding>(ActivitySingInBinding::inflate) {
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        runBlocking {
            userViewModel.getAllUserList()
        }

        Log.d(TAG, "onCreate: ${userViewModel.allUserList.value}")

        val userId = ApplicationClass.sharedPreferencesUtil.getAutoLogin()
        if (userId != null && userId != -1){
            runBlocking {
                userViewModel.getUser(userId,1)
            }
            if(userViewModel.isAutoLoginPossible.value == 1) {
                openFragment(1)
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.login_frame_layout, SingInFragment())
                    .commit()
            }

        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.login_frame_layout, SingInFragment())
                .commit()
        }

        Log.d(TAG, "onCreate: ${sha256("test")}\n${sha256("admin")}")
        initFcm()
    }

    fun openFragment(int : Int){
        val transaction = supportFragmentManager.beginTransaction()
        when(int){
            1->{
                val intent = Intent(this,MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            2->{
                transaction.replace(R.id.login_frame_layout, SingInFragment())
                    .addToBackStack(null)
            }
            3->{
                transaction.replace(R.id.login_frame_layout, SignUpFragment())
                    .addToBackStack(null)
            }
        }
        transaction.commit()
    }

    fun sha256(pw: String) : String {
        val hash: ByteArray
        try {
            val md = MessageDigest.getInstance("SHA-256")
            md.update(pw.toByteArray())
            hash = md.digest()
        } catch (e: CloneNotSupportedException) {
            throw DigestException("couldn't make digest of partial content");
        }

        return Base64.encodeToString(hash, Base64.NO_WRAP)
    }


    /**
     * FCM 토큰 수신 및 채널 생성
     */
    private fun initFcm() {
        // FCM 토큰 수신
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "FCM 토큰 얻기에 실패하였습니다.", task.exception)
                return@OnCompleteListener
            }
            // token log 남기기
            Log.d(TAG, "token: ${task.result?:"task.result is null"}")
            uploadToken(task.result!!, ApplicationClass.sharedPreferencesUtil.getUser().id)
        })

        createNotificationChannel(channel_id, "ssafy")
    }

    /**
     * Fcm Notification 수신을 위한 채널 추가
     * @author Jiwoo CHoi
     */
    private fun createNotificationChannel(id: String, name: String) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT // or IMPORTANCE_HIGH
        val channel = NotificationChannel(id, name, importance)

        val notificationManager: NotificationManager
                = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        const val channel_id = "ssafy_channel"
        fun uploadToken(token:String, userId: Int) {
//            val userService = ApplicationClass.retrofit.create(UserApi::class.java)

            var response : Response<User>
            runBlocking {
                response = RetrofitUtil.userService.updateUserDeviceToken(userId, token)
            }

            if(response.isSuccessful) {
                Log.d(TAG, "uploadToken: ${response.body()!!.deviceToken}")
            } else {
                Log.e(TAG, "uploadToken: 토큰 정보 등록 중 통신 오류")
            }
        }
    }
}