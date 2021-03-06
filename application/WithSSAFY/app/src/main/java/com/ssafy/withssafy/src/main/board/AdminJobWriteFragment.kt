package com.ssafy.withssafy.src.main.board

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.MaterialDatePicker
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentAdminJobWriteBinding
import com.ssafy.withssafy.src.dto.FcmRequest
import com.ssafy.withssafy.src.dto.Recruit
import com.ssafy.withssafy.src.main.MainActivity
import com.ssafy.withssafy.src.network.service.FcmService
import com.ssafy.withssafy.src.network.service.RecruitService
import com.ssafy.withssafy.src.network.service.StudyService
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

private const val TAG = "AdminJobWriteFragment"
class AdminJobWriteFragment : BaseFragment<FragmentAdminJobWriteBinding>(FragmentAdminJobWriteBinding::bind,R.layout.fragment_admin_job_write) {
    private var edu = ""
    private var prefer = ""
    private var employType = ""
    private var career = ""
    private var startDate = ""
    private var endDate = ""
    private var recruitId = 0
    private val STORAGE_CODE = 99
    private var fileExtension : String? = ""

    private lateinit var mainActivity : MainActivity
    val userId = ApplicationClass.sharedPreferencesUtil.getUser().id

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recruitId = it.getInt("recruitId")
        }
    }

    override fun onResume() {
        mainActivity.hideBottomNavi(true)
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.hideBottomNavi(true)

        if(recruitId != 0) {
            runBlocking {
                recruitViewModel.getRecruit(recruitId)
            }
        }
        setListener()
        initSpinner()
        selectSpinner()
        selectCheckBox()
        if(recruitId != 0) {
            initData()
        }
    }

    private fun setListener(){
        initButtons()
    }
    private fun initButtons(){
        binding.fragmentJobWriteAppBarPrev.setOnClickListener {
            this@AdminJobWriteFragment.findNavController().popBackStack()
        }

        binding.fragmentJobWriteDatePickerBtn.setOnClickListener {
            showDataRangePicker()
        }

        binding.fragmentJobWriteWrite.setOnClickListener {
            runBlocking {
                insertRecruit()
            }
        }
        binding.fragmentJobWriteCameraBtn.setOnClickListener {
            mainActivity.openGallery(STORAGE_CODE)
            if(teamViewModel.uploadImageUri != null){
                binding.fragmentJobWritePhotoGroup.visibility = View.VISIBLE
                Glide.with(requireContext())
                    .load(teamViewModel.uploadImageUri)
                    .into(binding.fragmentJobWriteCompanyImg)
            }
        }
        binding.fragmentJobWriteCompanyCancle.setOnClickListener {
            teamViewModel.uploadImageUri = null
            binding.fragmentJobWritePhotoGroup.visibility = View.GONE
        }
    }

    /**
     * ??????, ????????????, ???????????? spinner ?????????
     */
    private fun initSpinner() {
        var eduList = arrayListOf("??????", "????????????", "???????????? ????????????", "????????????(????????? ??????)")
        var preferList = arrayListOf("??????", "SSAFY ??????", "?????????????????? ????????? ?????????")
        var employTypeList = arrayListOf("??????", "?????????", "?????????")

        val eduSpin = binding.fragmentJobWriteEduSpinner
        val preferSpin = binding.fragmentJobWritePreferenceSpinner
        val employTypeSpin = binding.fragmentJobWriteCareerTypeSpinner

        eduSpin.apply {
            adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, eduList)
        }

        preferSpin.apply {
            adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, preferList.toList())
        }
        employTypeSpin.apply {
            adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, employTypeList.toList())
        }
    }

    /**
     * ??????????????? ???????????? ??? ?????? ????????? ?????????
     */
    private fun initData() {
        recruitViewModel.recruit.observe(viewLifecycleOwner) {
            val recruitData = it
            binding.fragmentJobWriteCompanyInfoNameEdit.setText(recruitData!!.company)
            binding.fragmentJobWriteCompanyInfoAddrEdit.setText(recruitData!!.location)
            binding.fragmentJobWriteCompanyInfoSalaryEdit.setText(recruitData!!.salary)
            binding.fragmentJobWriteCompanyInfoWorkTimeEdit.setText(recruitData!!.workingHours)
            binding.fragmentJobWriteJobEdit.setText(recruitData!!.job)
            binding.fragmentJobWriteTaskEdit.setText(recruitData!!.taskDescription)
            binding.fragmentJobWriteWelfareEdit.setText(recruitData!!.welfare)
            binding.fragmentJobWriteDatePickerBtn.setText("${recruitData.startDate}?????? ${recruitData.endDate}??????")
            val c = recruitData.career // ??????
            val e = recruitData.education // ??????
            val p = recruitData.preferenceDescription // ????????????
            val t = recruitData.employType // ????????????
            if(c == "??????") {
                binding.fragmentJobWriteAddInfoCareerNew.isChecked = true
            } else if(c == "??????") {
                binding.fragmentJobWriteAddInfoCareerSenior.isChecked = true
            }
            if(e == "????????????") {
                binding.fragmentJobWriteEduSpinner.setSelection(1)
            } else if(e == "???????????? ????????????") {
                binding.fragmentJobWriteEduSpinner.setSelection(2)
            } else if(e == "????????????(????????? ??????)") {
                binding.fragmentJobWriteEduSpinner.setSelection(3)
            }
            if(p == "SSAFY ??????") {
                binding.fragmentJobWritePreferenceSpinner.setSelection(1)
            } else if(p == "?????????????????? ????????? ?????????") {
                binding.fragmentJobWritePreferenceSpinner.setSelection(2)
            }
            if(t == "?????????") {
                binding.fragmentJobWriteCareerTypeSpinner.setSelection(1)
            } else if(t == "?????????") {
                binding.fragmentJobWriteCareerTypeSpinner.setSelection(2)
            }
        }
    }

    private fun selectSpinner() {
        val eduSpin = binding.fragmentJobWriteEduSpinner
        val preferSpin = binding.fragmentJobWritePreferenceSpinner
        val employTypeSpin = binding.fragmentJobWriteCareerTypeSpinner

        eduSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        edu = ""
                    } else -> {
                        edu = eduSpin.selectedItem.toString()
                    }
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        preferSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        prefer = ""
                    } else -> {
                        prefer = preferSpin.selectedItem.toString()
                    }
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        employTypeSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        employType = ""
                    } else -> {
                        employType = employTypeSpin.selectedItem.toString()
                        Log.d(TAG, "onItemSelected: $edu $prefer $employType")
                    }
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun selectCheckBox() {
        binding.fragmentJobWriteAddInfoCareerSenior.setOnCheckedChangeListener{ buttonView, isChecked ->
            if(isChecked) career = "??????"
            else  career = ""
            Log.d(TAG, "selectCheckBox: $career")
        }

        binding.fragmentJobWriteAddInfoCareerNew.setOnCheckedChangeListener{ buttonView, isChecked ->
            if(isChecked) career = "??????"
            else  career = ""
            Log.d(TAG, "selectCheckBox: $career")
        }
    }

    fun showDataRangePicker(){
        val dateRangePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select dates")
                .build()
        dateRangePicker.show(childFragmentManager, "date_picker")
        dateRangePicker.addOnPositiveButtonClickListener { selection ->
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = selection?.first ?: 0
            val sDate = selection.first
            startDate = SimpleDateFormat("yyyy-MM-dd").format(calendar.time).toString()
            Log.d("start", startDate)

            calendar.timeInMillis = selection?.second ?: 0
            val eDate = selection.second
            endDate = SimpleDateFormat("yyyy-MM-dd").format(calendar.time).toString()
            Log.d("end", endDate)

            binding.fragmentJobWriteDatePickerBtn.text = "${startDate} ~ ${endDate}"
        }
    }

    private suspend fun insertRecruit() {
        var career = career
        var company = binding.fragmentJobWriteCompanyInfoNameEdit.text.toString()
        var education = edu
        var employType = employType
        var endDate = endDate
        var job = binding.fragmentJobWriteJobEdit.text.toString()
        var location = binding.fragmentJobWriteCompanyInfoAddrEdit.text.toString()
        var preferenceDescription = prefer
        var salary = binding.fragmentJobWriteCompanyInfoSalaryEdit.text.toString()
        var startDate = startDate
        var taskDescription = binding.fragmentJobWriteTaskEdit.text.toString()
        var welfare = binding.fragmentJobWriteWelfareEdit.text.toString()
        var workingHours = binding.fragmentJobWriteCompanyInfoWorkTimeEdit.text.toString()
        var recruit = Recruit(
            career,
            company,
            education,
            employType,
            endDate,
            recruitId,
            job,
            location,
            preferenceDescription,
            salary,
            startDate,
            taskDescription,
            userId,
            welfare,
            workingHours,
            ""
        )

        if(recruitId == 0){
            if(teamViewModel.uploadImageUri == Uri.EMPTY || teamViewModel.uploadImageUri == null){
                runBlocking {
                    val response = RecruitService().insertRecruit(recruit)
                    Log.d(TAG, "insertRecruit: ${response.body()}")
                    Log.d(TAG, "insertRecruit: ${response.code()}")
                    if (response.code() == 204) {
                        showCustomToast("?????? ?????? ????????? ?????????????????????.")
                        showPushFcmDialog(recruit)
                    } else {
                        showCustomToast("?????? ?????? ????????? ??????????????????.")
                    }
                }
            }else{
                val file = File(teamViewModel.uploadImageUri!!.path!!)
                var inputStream: InputStream? = null
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
                            var recruit : Recruit? = null
                            recruit = Recruit(
                                career,
                                company,
                                education,
                                employType,
                                endDate,
                                recruitId,
                                job,
                                location,
                                preferenceDescription,
                                salary,
                                startDate,
                                taskDescription,
                                userId,
                                welfare,
                                workingHours,
                                responseFile.body().toString())
                            val response = RecruitService().insertRecruit(recruit)
                            if(response.code() == 204){
                                showCustomToast("?????????????????????.")
                                showPushFcmDialog(recruit)
//                                this@AdminJobWriteFragment.findNavController().popBackStack()
                            }
                        }
                    }
                }
            }
        }else{
            if(teamViewModel.uploadImageUri == Uri.EMPTY || teamViewModel.uploadImageUri == null) {
                runBlocking {
                    val response = RecruitService().updateRecruit(recruit)

                    if (response.code() == 204) {
                        showCustomToast("?????? ?????? ????????? ?????????????????????.")
                        this@AdminJobWriteFragment.findNavController().popBackStack()
                    } else {
                        showCustomToast("?????? ?????? ????????? ??????????????????.")
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
                            var recruit:Recruit?=null
                            recruit = Recruit(
                                career,
                                company,
                                education,
                                employType,
                                endDate,
                                recruitId,
                                job,
                                location,
                                preferenceDescription,
                                salary,
                                startDate,
                                taskDescription,
                                userId,
                                welfare,
                                workingHours,
                                responseFile.body().toString())
                            val response = RecruitService().updateRecruit(recruit!!)
                            if(response.isSuccessful){
                                showCustomToast("?????????????????????.")
                                this@AdminJobWriteFragment.findNavController().popBackStack()
                            }
                        }
                    }
                }
            }
        }
    }


    private fun showPushFcmDialog(recruit: Recruit) {
        AlertDialog.Builder(requireContext())
            .setTitle("")
            .setMessage("?????? ?????????????????? ?????? ????????? ??????????????????????")
            .setPositiveButton("??????", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    val response : Response<Any?>
                    runBlocking {
                        response = FcmService().broadCastMsg(FcmRequest(type = 3, title = recruit.company, body = recruit.job))
                    }
                    this@AdminJobWriteFragment.findNavController().popBackStack()
                    if(response.isSuccessful) {
                        showCustomToast("?????? ??????????????? ?????? ????????? ?????????????????????.")
                    }
                }
            })
            .setNegativeButton("??????", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    this@AdminJobWriteFragment.findNavController().popBackStack()
                }
            })
            .create()
            .show()
    }

    override fun onDestroyView() {
        mainActivity.hideBottomNavi(false)
        super.onDestroyView()
    }
}