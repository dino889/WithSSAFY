package com.ssafy.withssafy.src.login

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentSignUpBinding
import com.ssafy.withssafy.src.dto.User
import com.ssafy.withssafy.src.network.service.UserService
import com.ssafy.withssafy.util.RetrofitCallback
import kotlinx.coroutines.runBlocking
import java.util.regex.Pattern

private const val TAG = "SignUpFragment"
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::bind, R.layout.fragment_sign_up) {
    private lateinit var signInActivity: SingInActivity

    private var gen = ""
    private var area = ""
    private var classNum = ""
    private var classRoomId = -1

    override fun onAttach(context: Context) {
        super.onAttach(context)
        signInActivity = context as SingInActivity
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getClassRoomListInit()
        initSpinner()
        initListeners()

        binding.signUpFragmentBtnSignUp.setOnClickListener {
            var user = isAvailable()
            if(user != null) {
                Log.d(TAG, "onViewCreated: $user")
                signUp(user)
            } else {
                Toast.makeText(requireContext(), "입력 값을 다시 확인해 주세요", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getClassRoomListInit() {
        runBlocking {
            userViewModel.getClassRoomList()
        }
    }

    /**
     * TextInputEditText listener 등록
     */
    private fun initListeners() {
        binding.signUpFragmentEtId.addTextChangedListener(TextFieldValidation(binding.signUpFragmentEtId))
        binding.signUpFragmentEtPw.addTextChangedListener(TextFieldValidation(binding.signUpFragmentEtPw))
        binding.signUpFragmentEtName.addTextChangedListener(TextFieldValidation(binding.signUpFragmentEtName))
        binding.signUpFragmentEtStuId.addTextChangedListener(TextFieldValidation(binding.signUpFragmentEtStuId))
        selectSpinner()
    }

    /**
     * 기수, 반 spinner 초기화
     */
    private fun initSpinner() {
        var genList = arrayListOf("기수")
        var areaList = arrayListOf("지역")
        var classList = arrayListOf("반")

        val classRoomList = userViewModel.classRommList.value
        for(i in classRoomList!!) {
            genList.add(i.generation)
            areaList.add(i.area)
            classList.add(i.classDescription)
        }
        // 중복 제거
        val newGetList = genList.toSet()
        val newAreaList = areaList.toSet()
        val newClassList = classList.toSet()

        val genSpin = binding.signUpFragmentSpinnerStuGen
        val areaSpin = binding.signUpFragmentSpinnerArea
        val classSpin = binding.signUpFragmentSpinnerClass

        genSpin.apply {
            adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, newGetList.toList())
        }
        areaSpin.apply {
            adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, newAreaList.toList())
        }
        classSpin.apply {
            adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, newClassList.toList())
        }
    }

    private fun isAvailable() : User? {
        if((validatedId() && validatedPw() && validatedName() && validatedStuId()) || (gen != "" || area != "" || classNum != "")) {
            val id = binding.signUpFragmentEtId.text.toString()
            val pw = binding.signUpFragmentEtPw.text.toString()
            val name = binding.signUpFragmentEtName.text.toString()
            val stuId = binding.signUpFragmentEtStuId.text.toString()
            return User(id, pw, name, stuId, classRoomId)
        } else {
            return null
        }
    }

    /**
     * 입력된 id 유효성 검사
     * @return id 중복 통과 시 true 반환
     */
    private fun validatedId() : Boolean {
        val inputId = binding.signUpFragmentEtId.text.toString()

        if (inputId.trim().isEmpty()) {
            binding.signUpFragmentTilId.error = "Required Field"
            binding.signUpFragmentEtId.requestFocus()
            return false
        } else if(!Pattern.matches("^[가-힣ㄱ-ㅎa-zA-Z0-9._-]{4,20}\$", inputId)) {
            binding.signUpFragmentTilId.error = "아이디 형식을 확인해주세요.(특수 문자 제외)"
            binding.signUpFragmentEtId.requestFocus()
            return false
        } else {
            binding.signUpFragmentTilId.isErrorEnabled = false
            return true
        }
    }

    /**
     * 입력된 password 유효성 검사
     * @return 유효성 검사 통과 시 true 반환
     */
    private fun validatedPw() : Boolean {
        val inputPw = binding.signUpFragmentEtPw.text.toString()

        if(inputPw.trim().isEmpty()){   // 값이 비어있으면
            binding.signUpFragmentTilPw.error = "Required Field"
            binding.signUpFragmentEtPw.requestFocus()
            return false
        } else if(!Pattern.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@!%*#?&]).{8,50}.\$", inputPw)) {
            binding.signUpFragmentTilPw.error = "비밀번호 형식을 확인해주세요."
            binding.signUpFragmentEtPw.requestFocus()
            return false
        }
        else {
            binding.signUpFragmentTilPw.isErrorEnabled = false
            return true
        }
    }

    /**
     * 입력된 name 빈 칸 체크
     * @return 통과 시 true 반환
     */
    private fun validatedName() : Boolean{
        val inputNickname = binding.signUpFragmentEtName.text.toString()

        if(inputNickname.trim().isEmpty()){
            binding.signUpFragmentTilName.error = "Required Field"
            binding.signUpFragmentEtName.requestFocus()
            return false
        } else {
            binding.signUpFragmentTilName.error = null
            return true
        }
    }

    /**
     * 입력된 학번 빈 칸 체크
     * @return 통과 시 true 반환
     */
    private fun validatedStuId() : Boolean{
        val inputNickname = binding.signUpFragmentEtStuId.text.toString()

        if(inputNickname.trim().isEmpty()){
            binding.signUpFragmentTilStuId.error = "Required Field"
            binding.signUpFragmentEtStuId.requestFocus()
            return false
        } else {
            binding.signUpFragmentTilStuId.error = null
            return true
        }
    }

    private fun selectSpinner() {
        val genSpin= binding.signUpFragmentSpinnerStuGen
        val areaSpin = binding.signUpFragmentSpinnerArea
        val classSpin = binding.signUpFragmentSpinnerClass

        genSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        gen = ""
                    }
                    else -> {
                        gen = genSpin.selectedItem.toString()

                        if(gen != "" && area != "" && classNum != "") {
                            for(i in userViewModel.classRommList.value!!) {
                                if(i.generation == gen && i.area == area && i.classDescription == classNum) {
                                    classRoomId = i.id
                                }
                            }
                        }
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        areaSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        area = ""
                    }
                    else -> {
                        area = areaSpin.selectedItem.toString()

                        if(gen != "" && area != "" && classNum != "") {
                            for(i in userViewModel.classRommList.value!!) {
                                if(i.generation == gen && i.area == area && i.classDescription == classNum) {
                                    classRoomId = i.id
                                }
                            }
                        }
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        classSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        classNum = ""
                    }
                    else -> {
                        classNum = classSpin.selectedItem.toString()

                        if(gen != "" && area != "" && classNum != "") {
                            for(i in userViewModel.classRommList.value!!) {
                                if(i.generation == gen && i.area == area && i.classDescription == classNum) {
                                    classRoomId = i.id
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

    private fun signUp(user: User){
        UserService().signUp(user, object : RetrofitCallback<User> {
            override fun onError(t: Throwable) {
                Log.d(TAG, t.message?:"회원가입 통신오류")
            }

            override fun onSuccess(code: Int, responseData: User) {
                Toast.makeText(requireContext(), "회원가입이 완료되었습니다. 다시 로그인 해주세요.", Toast.LENGTH_SHORT).show()
                (requireActivity() as SingInActivity).onBackPressed()
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: resCode $code")
            }

        })
    }


    inner class TextFieldValidation(private val view: View): TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            when(view.id){
                R.id.signUpFragment_etId-> {
                    validatedId()
                }
                R.id.signUpFragment_etPw -> {
                    validatedPw()
                }
                R.id.signUpFragment_etName -> {
                    validatedName()
                }
                R.id.signUpFragment_etStuId -> {
                    validatedStuId()
                }
            }
        }
    }
}