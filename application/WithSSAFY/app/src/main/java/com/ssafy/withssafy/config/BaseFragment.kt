package com.ssafy.withssafy.config

import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import com.ssafy.withssafy.src.viewmodel.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment <B: ViewBinding> (
    private val bind: (View) -> B,
    @LayoutRes layoutResId: Int
): Fragment(layoutResId){

    private var _binding: B? = null
    protected val binding get() = _binding!!

//    protected val userId = ApplicationClass.sharedPreferencesUtil.getUser().id

    protected val homeViewModel: HomeViewModel by activityViewModels()
    protected val userViewModel: UserViewModel by activityViewModels()
    protected val teamViewModel: TeamViewModel by activityViewModels()
    protected val boardViewModel: BoardViewModel by activityViewModels()
    protected val messageViewModel : MessageViewModel by activityViewModels()
    protected val recruitViewModel : RecruitViewModel by activityViewModels()
    protected val scheduleViewModel : ScheduleViewModel by activityViewModels()
    protected val noticeViewModel : NoticeViewModel by activityViewModels()
    protected val notificationViewModel : NotificationViewModel by activityViewModels()

    protected val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bind(super.onCreateView(inflater, container, savedInstanceState)!!)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun showCustomToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    protected fun Disposable.addDisposable() {
        compositeDisposable.add(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}