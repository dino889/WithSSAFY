package com.ssafy.withssafy.src.main.team

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.gson.Gson
import com.jakewharton.rxbinding3.widget.itemSelections
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentTeamWriteBinding
import com.ssafy.withssafy.src.dto.study.Study
import com.ssafy.withssafy.src.dto.UserX
import com.ssafy.withssafy.src.dto.study.StudyMember
import com.ssafy.withssafy.src.main.MainActivity
import com.ssafy.withssafy.src.network.service.StudyService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Dispatcher
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream

private const val TAG = "TeamWriteFragment"
class TeamWriteFragment : BaseFragment<FragmentTeamWriteBinding>(FragmentTeamWriteBinding::bind,R.layout.fragment_team_write) {
    private var category = ""
    private var area = ""
    private var outing = -1
    private var fileExtension : String? = ""
    private lateinit var mainActivity:MainActivity
    private val STORAGE_CODE = 99
    var people = 0

    private var studyId = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            studyId = it.getInt("studyId")
        }
    }

    override fun onResume() {
        super.onResume()
        mainActivity.hideBottomNavi(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.hideBottomNavi(true)

        binding.viewModel = teamViewModel
        runBlocking {
            teamViewModel.getTeamInfo()
        }
        setListener()
    }
    private fun setListener(){
        initStudy()
        binding.fragmentTeamWriteTypeStudy.isChecked = true
        binding.fragmentTeamWriteTypeBuild.setOnCheckedChangeListener {buttonView, isChecked ->
            if(isChecked){
                binding.fragmentTeamWriteTypeStudy.isChecked = false
                initTeam()
            }else{
                binding.fragmentTeamWriteTypeStudy.isChecked = true
                initStudy()
            }
        }
        binding.fragmentTeamWriteTypeStudy.setOnCheckedChangeListener {buttonView, isChecked ->
            if(isChecked){
                binding.fragmentTeamWriteTypeBuild.isChecked = false
                initStudy()
            }else{
                binding.fragmentTeamWriteTypeBuild.isChecked = true
                initTeam()
            }
        }

        initButtons()
        if(studyId > 0){
            initData()
        }
    }
    private fun initTeam(){

        var classification = -1
        var minPeople = 0
        var maxPeople = 0
        var options = "없음"

        teamViewModel.teamInfo.observe(viewLifecycleOwner){
            classification = it.classification
            minPeople = it.minLimit
            maxPeople = it.maxLimit
            options = it.options
        }
        teamViewModel.flag = true
        teamViewModel.minPeople = minPeople
        teamViewModel.maxPeople = maxPeople

        var commonList = arrayListOf<String>("선택안함","웹 기술","웹 디자인","웹 IoT","모바일")
        var specializationList = arrayListOf<String>("선택안함","인공지능영상","인공지능음성","빅데이터추천","빅데이터분산","P2P거래","디지털화폐","IoT제어")
        var freeList = arrayListOf<String>("선택안함","자유주제","오픈소스","기업연계")

        if(classification == 0 || classification == -1){
            binding.fragmentTeamWriteStudyType.adapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, commonList)
        }else if(classification == 1){
            binding.fragmentTeamWriteStudyType.adapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, specializationList)
        }else if(classification == 2){
            binding.fragmentTeamWriteStudyType.adapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, freeList)
        }
        binding.fragmentTeamWriteStudyType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                category = binding.fragmentTeamWriteStudyType.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        var local = arrayListOf<String>("선택안함","서울","구미","대전","부울경","광주")
        binding.fragmentTeamWriteLoc.adapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, local)
        binding.fragmentTeamWriteLoc.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                area = binding.fragmentTeamWriteLoc.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        teamViewModel.count = minPeople
        teamViewModel.updateButtonText()

        binding.optionText.setText(options)

        binding.fragmentTeamWriteOnlineCheck.visibility = View.GONE
        binding.fragmentTeamWriteOfflineCheck.visibility = View.GONE
        binding.optionText.visibility = View.VISIBLE
    }
    private fun initStudy(){

        teamViewModel.flag = false
        //Spinner
        var categorys = arrayListOf<String>("선택안함","어학","프로그래밍","면접","취업","CS","자율","기타")
        binding.fragmentTeamWriteStudyType.adapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, categorys)
        binding.fragmentTeamWriteStudyType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                category = binding.fragmentTeamWriteStudyType.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        var local = arrayListOf<String>("선택안함","서울","경기","인천","강원","제주","대전","충북","충남/세종","부산","울산","경남","대구","경북","광주","전남","전주/전북")
        binding.fragmentTeamWriteLoc.adapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, local)
        binding.fragmentTeamWriteLoc.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                area = binding.fragmentTeamWriteLoc.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        //모집인원
        teamViewModel.count = 0;
        teamViewModel.updateButtonText()

        //옵션

        initCheckBox()
    }

    private fun initData(){
        runBlocking {
            teamViewModel.getStudy(studyId)
        }
        binding.fragmentTeamWriteSuccess.setText("수정")
        var study = teamViewModel.study.value!!
        binding.fragmentTeamWriteTitleEdit.setText(study.title.toString())
        binding.fragmentTeamWriteContentEdit.setText(study.content)
        binding.fragmentTeamWritePeople.setText(study.sbLimit.toString())
        teamViewModel.count = study.sbLimit
        teamViewModel.updateButtonText()

        if(study.photoPath == null || study.photoPath.toString() == ""){
            binding.fragmentTeamWritePhoto.visibility = View.GONE
        }else{
            Glide.with(requireContext())
                .load("${ApplicationClass.IMGS_URL}${study.photoPath}")
                .into(binding.fragmentTeamWritePhoto)
        }

        if(study.type == 0){
            binding.fragmentTeamWriteTypeStudy.isChecked = true
            binding.fragmentTeamWriteTypeBuild.isChecked = false

            var categorys = arrayListOf<String>("선택안함","어학","프로그래밍","면접","취업","CS","자율","기타")
            for(item in 0..categorys.size){
                if(study.category?.let { categorys[item].contains(it) } == true){
                    binding.fragmentTeamWriteStudyType.setSelection(item)
                    break
                }
            }

            var local = arrayListOf<String>("선택안함","서울","경기","인천","강원","제주","대전","충북","충남/세종","부산","울산","경남","대구","경북","광주","전남","전주/전북")
            for(item in 0..local.size){
                if(local[item].contains(study.area)){
                    Log.d(TAG, "initData: ${item}")
                    binding.fragmentTeamWriteLoc.setSelection(item)
                    break
                }
            }
            if(study.isOuting == 0){
                binding.fragmentTeamWriteOfflineCheck.isChecked = false
                binding.fragmentTeamWriteOnlineCheck.isChecked = true
            }else if(study.isOuting == 1){
                binding.fragmentTeamWriteOfflineCheck.isChecked = true
                binding.fragmentTeamWriteOnlineCheck.isChecked = false
            }
            binding.fragmentTeamWriteOnlineCheck.visibility = View.VISIBLE
            binding.fragmentTeamWriteOfflineCheck.visibility = View.VISIBLE
            binding.optionText.visibility = View.GONE

        }else{
            binding.fragmentTeamWriteTypeStudy.isChecked = false
            binding.fragmentTeamWriteTypeBuild.isChecked = true

            var classification = -1
            var options = ""
            var team = teamViewModel.teamInfo.value!!
            if(team != null){
                classification = team!!.classification
                options = team!!.options
            }
            var commonList = arrayListOf<String>("선택안함","웹 기술","웹 디자인","웹 IoT","모바일")
            var specializationList = arrayListOf<String>("선택안함","인공지능영상","인공지능음성","빅데이터추천","빅데이터분산","P2P거래","디지털화폐","IoT제어")
            var freeList = arrayListOf<String>("선택안함","자유주제","오픈소스","기업연계")

            if(classification == 0 || classification == -1){
                binding.fragmentTeamWriteStudyType.adapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, commonList)
                for(item in 0..commonList.size){
                    if(study.category.let { commonList[item].contains(it) }){
                        binding.fragmentTeamWriteStudyType.setSelection(item)
                        break
                    }
                }

            }else if(classification == 1){
                binding.fragmentTeamWriteStudyType.adapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, specializationList)
                for(item in 0..specializationList.size){
                    if(study.category.let { specializationList[item].contains(it) }){
                        binding.fragmentTeamWriteStudyType.setSelection(item)
                        break
                    }
                }

            }else if(classification == 2){
                binding.fragmentTeamWriteStudyType.adapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, freeList)
                for(item in 0..freeList.size){
                    if(study.category.let { freeList[item].contains(it) }){
                        binding.fragmentTeamWriteStudyType.setSelection(item)
                        break
                    }
                }
            }
            var local = arrayListOf<String>("선택안함","서울","구미","대전","부울경","광주")
            for(item in 0..local.size){
                if(local[item].contains(study.area)){
                    Log.d(TAG, "initData: ${item}")
                    binding.fragmentTeamWriteLoc.setSelection(item)
                    break
                }
            }
            binding.optionText.text = options
            binding.fragmentTeamWriteOnlineCheck.visibility = View.GONE
            binding.fragmentTeamWriteOfflineCheck.visibility = View.GONE
            binding.optionText.visibility = View.VISIBLE
        }
    }
    private fun initCheckBox(){
        binding.fragmentTeamWriteOnlineCheck.visibility = View.VISIBLE
        binding.fragmentTeamWriteOfflineCheck.visibility = View.VISIBLE
        binding.optionText.visibility = View.GONE
        
        binding.fragmentTeamWriteOnlineCheck.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                binding.fragmentTeamWriteOfflineCheck.isChecked = false
                 outing = 0
            }else{
                binding.fragmentTeamWriteOfflineCheck.isChecked = true
                outing = 1
            }
        }
        binding.fragmentTeamWriteOfflineCheck.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                binding.fragmentTeamWriteOnlineCheck.isChecked = false
                outing = 1
            }else{
                binding.fragmentTeamWriteOnlineCheck.isChecked =false
                outing = 0
            }
        }
    }
    private fun initSpinner(){
        var categorys = arrayListOf<String>("선택안함","어학","프로그래밍","면접","취업","CS","자율","기타")
        binding.fragmentTeamWriteStudyType.adapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, categorys)
        binding.fragmentTeamWriteStudyType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                category = binding.fragmentTeamWriteStudyType.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        var local = arrayListOf<String>("선택안함","서울","경기","인천","강원","제주","대전","충북","충남/세종","부산","울산","경남","대구","경북","광주","전남","전주/전북")
        binding.fragmentTeamWriteLoc.adapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, local)
        binding.fragmentTeamWriteLoc.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                area = binding.fragmentTeamWriteLoc.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }
    private fun initButtons(){
        binding.fragmentTeamWriteAppBarPrev.setOnClickListener {
            this@TeamWriteFragment.findNavController().popBackStack()
        }
        binding.fragmentTeamWriteSuccess.setOnClickListener {
            if(studyId > 0){
                if(validateData()){
                    runBlocking { updateStudy() }
                }
            }else{
                if(validateData()){
                    runBlocking { insertStudy() }
                }
            }

        }
        binding.fragmentTeamWriteCamera.setOnClickListener {
            mainActivity.openGallery(STORAGE_CODE)
            if(teamViewModel.uploadImageUri!=null){
                binding.fragmentTeamWritePhotoGroup.visibility = View.VISIBLE
                Glide.with(requireContext())
                    .load(teamViewModel.uploadImageUri)
                    .into(binding.fragmentTeamWritePhoto)
            }
        }
        binding.fragmentTeamWritePhotoDelete.setOnClickListener {
            teamViewModel.uploadImageUri = null
            binding.fragmentTeamWritePhotoGroup.visibility = View.GONE
        }
    }
    private fun validateData() : Boolean{
        var peopleChecked = false
        var contentChecked = false
        var LocChecked = false
        var typeChecked = false
        var optionChecked = false

        if(binding.fragmentTeamWritePeople.text.toString().toInt() > 0 ){
            peopleChecked = true
        }else{
            showCustomToast("모집인원이 부족합니다.")
            peopleChecked = false
        }

        if(binding.fragmentTeamWriteContentEdit.text.toString().length > 10){
            contentChecked = true
        }else{
            showCustomToast("최소 내용의 길이는 10 이상입니다.")
            contentChecked = false
        }

        if(binding.fragmentTeamWriteLoc.selectedItemPosition != 0){
            LocChecked = true
        }else{
            showCustomToast("지역을 선택해주세요")
            LocChecked = false
        }

        if(binding.fragmentTeamWriteStudyType.selectedItemPosition != 0){
            typeChecked = true
        }else{
            showCustomToast("분야를 선택해주세요")
            typeChecked = false
        }

        if(!binding.fragmentTeamWriteTypeBuild.isChecked){
            if(binding.fragmentTeamWriteOnlineCheck.isChecked == true || binding.fragmentTeamWriteOfflineCheck.isChecked == true){
                optionChecked = true
            }else if(binding.fragmentTeamWriteOnlineCheck.isChecked == true && binding.fragmentTeamWriteOfflineCheck.isChecked == true){
                showCustomToast("옵션을 하나만 선택해주세요")
                optionChecked = false
            }else{
                showCustomToast("옵션을 선택해주세요")
                optionChecked = false
            }
        }else{
            optionChecked = true
        }


        if(optionChecked && typeChecked && LocChecked && peopleChecked && contentChecked){
            return true
        }else{
            return false
        }
    }
    private suspend fun updateStudy(){
        var local = area
        var category = category
        var content = binding.fragmentTeamWriteContentEdit.text.toString()
        var isOuting = outing
        var limit = binding.fragmentTeamWritePeople.text.toString()
        var title = binding.fragmentTeamWriteTitleEdit.text.toString()
        var today = System.currentTimeMillis()

        var study:Study?= null

        if(binding.fragmentTeamWriteTypeBuild.isChecked){
            study = Study(
                local,
                category,
                content,
                isOuting,
                limit.toInt(),
                title,
                today.toString(),
                ApplicationClass.sharedPreferencesUtil.getUser().id,
                1
            )
        }else{
            study = Study(
                local,
                category,
                content,
                isOuting,
                limit.toInt(),
                title,
                today.toString(),
                ApplicationClass.sharedPreferencesUtil.getUser().id,
                0
            )
        }

        if(teamViewModel.uploadImageUri == Uri.EMPTY || teamViewModel.uploadImageUri == null){
            runBlocking {
                val response = StudyService().updateStudy(studyId,study!!)
                if(response.code() == 204){
                    showCustomToast("수정되었습니다.")
                    this@TeamWriteFragment.findNavController().navigate(R.id.teamFragment)
                }
            }
        }else{
            val file = File(teamViewModel.uploadImageUri!!.path!!)
            var inputStream:InputStream? = null
            try{
                inputStream = mainActivity.contentResolver.openInputStream(teamViewModel.uploadImageUri!!)
            }catch (e : IOException){
                e.printStackTrace()
            }
            fileExtension = mainActivity.contentResolver.getType(teamViewModel.uploadImageUri!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG,20,byteArrayOutputStream)
            val requestBody = RequestBody.create(MediaType.parse("image/*"),byteArrayOutputStream.toByteArray())
            val uploadFile = MultipartBody.Part.createFormData("file","${file.name}.${fileExtension?.substring(6)}",requestBody)
            runBlocking {
                val responseFile = StudyService().insertPhoto(uploadFile)
                if(responseFile.code() == 200){
                    if(responseFile.body() != null){
                        Log.d(TAG, "insertStudy: ${responseFile.body()}")
                        var study:Study ?= null
                        if(binding.fragmentTeamWriteTypeBuild.isChecked){
                            study = Study(
                                local,
                                category,
                                content,
                                0,
                                isOuting,
                                limit.toInt(),
                                title,
                                today.toString(),
                                responseFile.body().toString(),
                                ApplicationClass.sharedPreferencesUtil.getUser().id,
                                1
                            )
                        }else{
                            study = Study(
                                local,
                                category,
                                content,
                                0,
                                isOuting,
                                limit.toInt(),
                                title,
                                today.toString(),
                                responseFile.body().toString(),
                                ApplicationClass.sharedPreferencesUtil.getUser().id,
                                0
                            )
                        }


                        val response = StudyService().updateStudy(studyId,study!!)
                        if(response.code() == 204){
                            showCustomToast("수정되었습니다.")
                            this@TeamWriteFragment.findNavController().navigate(R.id.teamFragment)
                        }
                    }
                }
            }
        }
    }
    private suspend fun insertStudy(){
        var local = area
        var category = category
        var content = binding.fragmentTeamWriteContentEdit.text.toString()
        var isOuting = outing
        var limit = binding.fragmentTeamWritePeople.text.toString()
        var title = binding.fragmentTeamWriteTitleEdit.text.toString()
        var today = System.currentTimeMillis()
        var study:Study? = null
        if(binding.fragmentTeamWriteTypeBuild.isChecked){
            study = Study(
                local,
                category,
                content,
                isOuting,
                limit.toInt(),
                title,
                today.toString(),
                ApplicationClass.sharedPreferencesUtil.getUser().id,
                1
            )
        }else{
            study = Study(
                local,
                category,
                content,
                isOuting,
                limit.toInt(),
                title,
                today.toString(),
                ApplicationClass.sharedPreferencesUtil.getUser().id,
                0
            )
        }

        Log.d(TAG, "insertStudy: $study")
        if(teamViewModel.uploadImageUri == Uri.EMPTY || teamViewModel.uploadImageUri == null){
            runBlocking {
                val response = StudyService().insertStudy(study!!)
                if(response.code() == 204){
                    showCustomToast("추가되었습니다.")
                    this@TeamWriteFragment.findNavController().navigate(R.id.teamFragment)
                }
            }
        }else{
            val file = File(teamViewModel.uploadImageUri!!.path!!)
            var inputStream:InputStream? = null
            try{
                inputStream = mainActivity.contentResolver.openInputStream(teamViewModel.uploadImageUri!!)
            }catch (e : IOException){
                e.printStackTrace()
            }
            fileExtension = mainActivity.contentResolver.getType(teamViewModel.uploadImageUri!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG,20,byteArrayOutputStream)
            val requestBody = RequestBody.create(MediaType.parse("image/*"),byteArrayOutputStream.toByteArray())
            val uploadFile = MultipartBody.Part.createFormData("file","${file.name}.${fileExtension?.substring(6)}",requestBody)
            runBlocking {
                val responseFile = StudyService().insertPhoto(uploadFile)
                if(responseFile.code() == 200){
                    if(responseFile.body() != null){
                        Log.d(TAG, "insertStudy: ${responseFile.body()}")
                        var study:Study?= null
                        if(binding.fragmentTeamWriteTypeBuild.isChecked){
                            study = Study(
                                local,
                                category,
                                content,
                                0,
                                isOuting,
                                limit.toInt(),
                                title,
                                today.toString(),
                                responseFile.body().toString(),
                                ApplicationClass.sharedPreferencesUtil.getUser().id,
                                1
                            )
                        }else{
                            study = Study(
                                local,
                                category,
                                content,
                                0,
                                isOuting,
                                limit.toInt(),
                                title,
                                today.toString(),
                                responseFile.body().toString(),
                                ApplicationClass.sharedPreferencesUtil.getUser().id,
                                0
                            )
                        }
                        val response = StudyService().insertStudy(study!!)
                        if(response.code() == 204){
                            showCustomToast("추가되었습니다.")
                            this@TeamWriteFragment.findNavController().navigate(R.id.teamFragment)
                        }
                    }
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivity.hideBottomNavi(false)
    }
}