package com.ssafy.withssafy.src.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseActivity
import com.ssafy.withssafy.databinding.ActivityLoadingBinding
import com.ssafy.withssafy.src.login.SingInActivity

class LoadingActivity : BaseActivity<ActivityLoadingBinding>(ActivityLoadingBinding::inflate) {
    private val SPLASH_TIME:Long = 6000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({
            startActivity(Intent(this,SingInActivity::class.java))
            finish()
        },SPLASH_TIME)
    }
}