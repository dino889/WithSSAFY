package com.ssafy.withssafy.src.main.home

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentUserBinding
import com.ssafy.withssafy.src.main.MainActivity
import com.ssafy.withssafy.src.network.service.UserService
import com.ssafy.withssafy.util.RetrofitCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.runBlocking

private const val TAG = "UserFragment"
class UserFragment :
    BaseFragment<FragmentUserBinding>(FragmentUserBinding::bind, R.layout.fragment_user) {
    private lateinit var mainActivity: MainActivity

    val userId = ApplicationClass.sharedPreferencesUtil.getUser().id

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onResume() {
        super.onResume()
        mainActivity.hideBottomNavi(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        runBlocking {
            userViewModel.getClassRoom(userId)
        }

        setListener()
        initUserData()
    }

    private fun initUserData() {
         userViewModel.loginUserInfo.observe(viewLifecycleOwner) {
             binding.fragmentUserInfoName.text = it.name
             binding.fragmentUserInfoSId.text = it.studentId
             binding.fragmentUserInfoId.text = "id ${it.userId}"
         }
        userViewModel.classRoomInfo.observe(viewLifecycleOwner) {
            binding.fragmentUserInfoVer.text = "SSAFY ${it.generation}"
            binding.fragmentUserInfoLoc.text = it.area
            binding.fragmentUserInfoBan.text = it.classDescription

        }
    }



    private fun setListener() {
        initButtons()
    }

    private fun initButtons() {
        binding.fragmentUserAppBarPrev.setOnClickListener {
            this@UserFragment.findNavController().popBackStack()
        }

        binding.fragmentUserAccountInfoPwChange.setOnClickListener {
            this@UserFragment.findNavController().navigate(R.id.action_userFragment_to_userInfoChangeFragment,
                bundleOf("changeType" to 0)  // 0 - 비밀번호 변경
            )
        }

        binding.fragmentUserAccountInfoClassChange.setOnClickListener {
            this@UserFragment.findNavController().navigate(R.id.action_userFragment_to_userInfoChangeFragment,
                bundleOf("changeType" to 1)  // 1 - 반정보 변경
            )
        }

        binding.fragmentUserAccountLogout.setOnClickListener {
            mainActivity.logout()
        }

        binding.fragmentUserAccountDelete.setOnClickListener {
            showDeleteUserDialog()
        }
    }

    private fun showDeleteUserDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("회원 탈퇴")
            .setMessage("정말로 탈퇴하시겠습니까?")
            .setPositiveButton("YES", DialogInterface.OnClickListener { dialogInterface, id ->
                UserService().deleteUser(ApplicationClass.sharedPreferencesUtil.getUser().id, DeleteCallback())
            })
            .setNeutralButton("NO", null)
            .create()

        builder.show()
    }

    inner class DeleteCallback() : RetrofitCallback<Boolean> {
        override fun onError(t: Throwable) {
            Log.d(TAG, "onError: ")
        }

        override fun onSuccess(code: Int, responseData: Boolean) {
            if(responseData){
                showCustomToast("탈퇴되었습니다.")
                mainActivity.logout()
            }
        }

        override fun onFailure(code: Int) {
            Log.d(TAG, "onFailure: ")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}