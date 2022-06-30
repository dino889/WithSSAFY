package com.ssafy.withssafy.src.main.home

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentUserInfoChangeBinding
import com.ssafy.withssafy.src.dto.User
import com.ssafy.withssafy.src.main.MainActivity
import com.ssafy.withssafy.src.network.service.UserService
import com.ssafy.withssafy.util.RetrofitCallback
import kotlinx.coroutines.runBlocking
import java.security.DigestException
import java.security.MessageDigest
import java.util.regex.Pattern

private const val TAG = "UserInfoChangeFragment"
class UserInfoChangeFragment : BaseFragment<FragmentUserInfoChangeBinding>(FragmentUserInfoChangeBinding::bind, R.layout.fragment_user_info_change) {
    private lateinit var mainActivity: MainActivity
    private var changeType: Int = -1
    private var gen = ""
    private var area = ""
    private var newClass = ""
    private var newClassRoomId = -1


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            changeType = getInt("changeType")
        }
        mainActivity.hideBottomNavi(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        initListeners()

        binding.pwChangeBack.setOnClickListener{
            this@UserInfoChangeFragment.findNavController().popBackStack()
        }

        binding.classChangeBack.setOnClickListener{
            this@UserInfoChangeFragment.findNavController().popBackStack()
        }

        binding.pwChangeBtn.setOnClickListener {
            if(validatedPw() && validatedAgainPw()) {
                val pw = binding.pwChangeEt.text.toString()
                val shaPw = sha256(pw)
                val userId = ApplicationClass.sharedPreferencesUtil.getUser().id
                UserService().updatePw(shaPw, userId, UpdateCallback())
            } else {
                showCustomToast("입력 값을 다시 확인해 주세요")
            }
        }

        binding.classChangeBtn.setOnClickListener {
            if(newClassRoomId != -1) {
                val userId = ApplicationClass.sharedPreferencesUtil.getUser().id
                UserService().updateClass(newClassRoomId, userId, UpdateCallback())
            } else {
                showCustomToast("반정보 변경에 실패했습니다.")
            }
        }
    }

    private fun getClassRoomInit() {
        runBlocking {
            userViewModel.getClassRoom(ApplicationClass.sharedPreferencesUtil.getUser().id)
            userViewModel.getClassRoomList()
        }
    }

    private fun initSpinner() {
        var classList = arrayListOf("반")

        val classRoomList = userViewModel.classRommList.value

        for(i in classRoomList!!) {
            if(i.generation == gen && i.area == area) {
                classList.add(i.classDescription)
            }
        }
        // 중복 제거
        val newClassList = classList.toSet()

        val classSpin = binding.classChangeSpinner

        classSpin.apply {
            adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, newClassList.toList())
        }
    }

    private fun initLayout() {
        if(changeType == 0) {
            binding.layoutPwChange.visibility = View.VISIBLE
            binding.layoutClassChange.visibility = View.GONE
        } else if(changeType == 1) {
            binding.layoutPwChange.visibility = View.GONE
            binding.layoutClassChange.visibility = View.VISIBLE
            getClassRoomInit()
            initData()

        }
    }

    private fun initListeners() {
        binding.pwChangeEt.addTextChangedListener(TextFieldValidation(binding.pwChangeEt))
        binding.pwChangeAgainEt.addTextChangedListener(TextFieldValidation(binding.pwChangeAgainEt))
        selectSpinner()
    }

    private fun initData() {
        userViewModel.classRoomInfo.observe(viewLifecycleOwner) {
            gen = it.generation
            area = it.area
            binding.changeClassGenTxtContent.text = gen
            binding.changeClassAreaTxtContent.text = area
            binding.changeClassClassTxtContent.text = it.classDescription
            initSpinner()
        }
    }

    private fun validatedPw() : Boolean {
        val inputPw = binding.pwChangeEt.text.toString()

        if(inputPw.trim().isEmpty()){   // 값이 비어있으면
            binding.pwChangeTil.error = "Required Field"
            binding.pwChangeEt.requestFocus()
            return false
        } else if(!Pattern.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@!%*#?&]).{8,50}.\$", inputPw)) {
            binding.pwChangeTil.error = "비밀번호 형식을 확인해주세요."
            binding.pwChangeEt.requestFocus()
            return false
        }
        else {
            binding.pwChangeTil.isErrorEnabled = false
            return true
        }
    }

    private fun validatedAgainPw() : Boolean {
        val inputPw = binding.pwChangeAgainEt.text.toString()

        if(inputPw.trim().isEmpty()){   // 값이 비어있으면
            binding.pwChangeAgainTil.error = "Required Field"
            binding.pwChangeAgainEt.requestFocus()
            return false
        } else if(binding.pwChangeEt.text.toString() != inputPw) {
            binding.pwChangeAgainTil.error = "비밀번호가 다릅니다."
            binding.pwChangeAgainEt.requestFocus()
            return false
        }
        else {
            binding.pwChangeAgainTil.isErrorEnabled = false
            return true
        }
    }

    private fun selectSpinner() {
        val newClassSpin = binding.classChangeSpinner

        newClassSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        newClass = ""
                    }
                    else -> {
                        newClass = newClassSpin.selectedItem.toString()

                        if(gen != "" && area != "" && newClass != "") {
                            for(i in userViewModel.classRommList.value!!) {
                                if(i.generation == gen && i.area == area && i.classDescription == newClass) {
                                    newClassRoomId = i.id
                                }
                            }
                        }
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
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

    inner class UpdateCallback() : RetrofitCallback<User> {
        override fun onError(t: Throwable) {
            Log.d(TAG, "onError: ")
        }

        override fun onSuccess(code: Int, responseData: User) {
            if(responseData != null) {
                showCustomToast("변경 완료되었습니다.")
                this@UserInfoChangeFragment.findNavController().popBackStack()
            } else {
                showCustomToast("변경을 실패했습니다.")
            }
        }

        override fun onFailure(code: Int) {
            Log.d(TAG, "onFailure: ")
        }
    }

    inner class TextFieldValidation(private val view: View): TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            when(view.id){
                R.id.pw_change_et-> {
                    validatedPw()
                }
                R.id.pw_change_again_et -> {
                    validatedAgainPw()
                }
            }
        }
    }

}