package com.ssafy.withssafy.src.login

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentSingInBinding
import com.ssafy.withssafy.src.dto.User
import com.ssafy.withssafy.src.network.response.UserInfoResponse
import com.ssafy.withssafy.src.network.service.UserService
import com.ssafy.withssafy.util.RetrofitCallback

private const val TAG = "SingInFragment"
class SingInFragment : BaseFragment<FragmentSingInBinding>(FragmentSingInBinding::bind,R.layout.fragment_sing_in) {
    private lateinit var signInActivity: SingInActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        signInActivity = context as SingInActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()

    }

    private fun initListener() {
        forgotPwBtnClickEvent()
        signUpBtnClickEvent()
        loginBtnClickEvent()
    }

    /**
     * Forgot Password 버튼 클릭 이벤트
     */
    private fun forgotPwBtnClickEvent() {
        binding.signInFragmentLostPwTv.setOnClickListener {
            signInActivity.openFragment(1)
        }
    }

    /**
     * 회원가입 버튼 클릭 이벤트
     */
    private fun signUpBtnClickEvent() {
        binding.signInFragmentSignUpBtn.setOnClickListener {
            signInActivity.openFragment(3)
        }
    }

    /**
     * 로그인 버튼 클릭 이벤트
     */
    private fun loginBtnClickEvent() {
        binding.signInFragmentLoginBtn.setOnClickListener {
            login(binding.signInFragmentEtId.text.toString(), signInActivity.sha256(binding.signInFragmentEtPw.text.toString()))
        }
    }

    private fun login(loginId: String, loginPass: String) {
        val user = User(loginId, loginPass)
        UserService().login(user, LoginCallback())
    }

    inner class LoginCallback: RetrofitCallback<UserInfoResponse> {
        override fun onSuccess(code: Int, user: UserInfoResponse) {
            if (code == 200) {
                Log.d(TAG, "onSuccess: ${user}")
                if(user.studentId != null && user.state == 0) {
                    showCustomToast("관리자 승인이 진행중입니다.")
                } else {
                    showCustomToast("로그인 되었습니다.")
                    ApplicationClass.sharedPreferencesUtil.addUser(User(user.id, user.password, user.studentId), user.classRoomDto.id)
                    if(binding.signInFragmentCbAutoLogin.isChecked) {
                        ApplicationClass.sharedPreferencesUtil.setAutoLogin(user.id)
                    }
                    signInActivity.openFragment(1)
                }
            }else if(code == 500){
                showCustomToast("ID 또는 패스워드를 확인해 주세요.")
            }
        }
        override fun onError(t: Throwable) {
            Log.d(TAG, t.message ?: "유저 정보 불러오는 중 통신오류")

        }

        override fun onFailure(code: Int) {
            Log.d(TAG, "onResponse: Error Code $code")
            Toast.makeText(context, "ID 또는 패스워드를 확인해 주세요.", Toast.LENGTH_SHORT).show()
        }
    }
}